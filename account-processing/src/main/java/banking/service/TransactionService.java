package banking.service;

import banking.model.Status;
import banking.model.Account;
import banking.model.dto.PaymentAmountDTO;
import banking.model.dto.TransactionDTO;
import banking.repository.AccountRepository;
import banking.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TransactionService {

    private final RestTemplate restTemplate;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;


    @Value("${transaction.limit}")
    private int transactionLimit;


    @Value("${transaction.time}")
    private int timeWindowMinutes;

    @KafkaListener(topics = "client_transactions",
            containerFactory = "transactionKafkaListenerContainerFactory")
    public void consumeTransaction(TransactionDTO message) {
        accountService.processTransaction(message);
    }

    public boolean isLimitExceeded(String cardId) {
        LocalDateTime from = LocalDateTime.now().minusMinutes(timeWindowMinutes);
        long count = transactionRepository.countRecentTransactions(cardId, from);
        return count > transactionLimit;
    }

    @Transactional
    public void processMonthlyCreditPayment(Long accountId) {

        Account account = accountRepository.getAccountById(accountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (account.getIsRecalc() == null || !account.getIsRecalc()) {
            return;
        }
        String url = "http://localhost:8082/productRegistry/" + accountId + "/payment?paymentDate=" + LocalDateTime.now().toLocalDate();
        PaymentAmountDTO paymentDTO = restTemplate.getForObject(url, PaymentAmountDTO.class);

        if (paymentDTO == null || paymentDTO.getAmount() == null || paymentDTO.getAmount() <= 0) {
            return;
        }

        double paymentAmount = paymentDTO.getAmount();

        if (account.getBalance() >= paymentAmount) {
            account.setBalance(account.getBalance() - paymentAmount);
            accountRepository.save(account);

            TransactionDTO transaction = new TransactionDTO();
            transaction.setAccountId(account.getId());
            transaction.setType("WITHDRAW");
            transaction.setAmount(paymentAmount);
            transaction.setTimestamp(LocalDateTime.now());
            accountService.processTransaction(transaction);
        }

    }
}


