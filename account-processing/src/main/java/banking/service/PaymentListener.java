package banking.service;

import banking.model.Account;
import banking.model.dto.PaymentDTO;
import banking.repository.AccountRepository;
import banking.repository.TransactionRepository;
import banking.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PaymentListener {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentService paymentService;

    @KafkaListener(topics = "client_payments",
            containerFactory = "paymentKafkaListenerContainerFactory")
    public void consumePayment(PaymentDTO message) {
        paymentService.processPayment(message);
    }

}
