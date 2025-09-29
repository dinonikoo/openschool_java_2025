package banking.service;

import banking.model.Status;
import banking.model.Account;
import banking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    @KafkaListener(topics = "client_transactions", groupId = "transaction-group")
    public void consume(String message) {
        // just print
        //System.out.println("Message: " + message);
    }
}
