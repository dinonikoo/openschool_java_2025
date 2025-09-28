package banking.service;

import banking.model.Status;
import banking.model.dto.ClientProductResponse;
import banking.model.Account;
import banking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @KafkaListener(
            topics = "client_products",
            groupId = "account-service",
            containerFactory = "clientKafkaListenerContainerFactory",
            properties = {"spring.json.value.default.type=banking.model.dto.ClientProductResponse"}
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
}
