package banking.service;

import banking.model.Account;
import banking.model.Payment;
import banking.model.dto.AccountInfoDTO;
import banking.model.dto.PaymentDTO;
import banking.repository.AccountRepository;
import banking.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String productRegistryUrl = "http://localhost:8082/productRegistry";

    @Transactional
    public void processPayment(PaymentDTO msg) {
        Account account = accountRepository.findById(msg.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (account.getIsRecalc() == null || !account.getIsRecalc()) {
            return;
        }
        AccountInfoDTO clientInfo = restTemplate.getForObject(
                productRegistryUrl + "/" + account.getId(),
                AccountInfoDTO.class
        );

        if (clientInfo == null || clientInfo.getTotalDebt() == null) return;

        double totalDebt = clientInfo.getTotalDebt();
        if (msg.getAmount() < totalDebt) {
            return;
        }

        if (account.getBalance() == null) account.setBalance(0.0);
        account.setBalance(account.getBalance() + msg.getAmount());
        accountRepository.save(account);

        Payment payment = new Payment();
        payment.setAccount(account);
        payment.setAmount(msg.getAmount());
        payment.setPaymentDate(LocalDate.from(msg.getPaymentDate()));
        payment.setPayedAt(java.time.LocalDateTime.now());
        paymentRepository.save(payment);

        String message = "Credit fully paid for accountId: " + account.getId();
        kafkaTemplate.send("client_credit_products", account.getId().toString(), message);
    }

}