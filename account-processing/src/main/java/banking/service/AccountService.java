package banking.service;

import banking.model.Status;
import banking.model.Transaction;
import banking.model.TransactionStatus;
import banking.model.dto.ClientProductResponse;
import banking.model.Account;
import banking.model.dto.TransactionDTO;
import banking.repository.AccountRepository;
import banking.repository.TransactionRepository;
import banking.utils.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;

    @KafkaListener(
            topics = "client_products",
            containerFactory = "clientKafkaListenerContainerFactory",
            groupId = "account-group"//,
            //properties = {"spring.json.value.default.type=banking.model.dto.ClientProductResponse"}
    )
    public void handleClientProduct(ClientProductResponse event) {
        System.out.println("Got message from ClientProduct: " + event);

        Account account = new Account();
        account.setClientId(event.getClientId());
        account.setProductId(event.getProductId());
        account.setBalance(0.0);
        account.setInterestRate(getRate(event.getProductKey()));
        account.setIsRecalc(false);
        account.setCardExist(false);
        account.setStatus(Status.valueOf(event.getStatus()));

        accountRepository.save(account);
        System.out.println("Account created for client " + event.getClientId());
    }

    private double getRate(String productKey) {
        return switch (productKey) {
            case "DC" -> 0.01;
            case "CC" -> 0.05;
            default -> 0.0;
        };
    }

    @Transactional
    public void processTransaction(TransactionDTO msg) {
        Account account = accountRepository.findById(msg.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (account.getStatus() == Status.CLOSED || account.getStatus() == Status.ARRESTED) {
            Transaction tx = transactionMapper.fromMessage(msg);
            tx.setStatus(TransactionStatus.BLOCKED);
            transactionRepository.save(tx);
            return;
        }

        // PROCESSING
        Transaction tx = transactionMapper.fromMessage(msg);
        tx.setStatus(TransactionStatus.PROCESSING);
        transactionRepository.save(tx);

        boolean success;
        switch (msg.getType()) {
            case "DEPOSIT" -> {
                success = deposit(account, msg.getAmount());
                if (success && Boolean.TRUE.equals(account.getIsRecalc())) {
                    transactionService.processMonthlyCreditPayment(account.getId());
                }
            }
            case "WITHDRAW" -> success = withdraw(account, msg.getAmount());
            default -> throw new IllegalArgumentException("Unsupported transaction type: " + msg.getType());
        }

        if (!success) {
            tx.setStatus(TransactionStatus.CANCELLED);
            transactionRepository.save(tx);
            return;
        }

        if (transactionService.isLimitExceeded(msg.getCardId())) {
            account.setStatus(Status.ARRESTED);
        }

        tx.setStatus(TransactionStatus.COMPLETE);
        transactionRepository.save(tx);
        accountRepository.save(account);
    }

    private boolean deposit(Account account, Double amount) {
        if (amount == null || amount <= 0) return false;
        if (account.getBalance() == null) account.setBalance(0.0);
        account.setBalance(account.getBalance() + amount);
        return true;
    }

    private boolean withdraw(Account account, Double amount) {
        if (amount == null || amount <= 0) return false;
        if (account.getBalance() == null || account.getBalance() < amount) return false;
        account.setBalance(account.getBalance() - amount);
        return true;
    }

}
