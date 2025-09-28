package banking.service;

import banking.model.Account;
import banking.model.Card;
import banking.model.Status;
import banking.model.dto.CardCreateRequest;
import banking.repository.AccountRepository;
import banking.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    @KafkaListener(
            topics = "client_cards",
            groupId = "card-service",
            containerFactory = "cardKafkaListenerContainerFactory",
            properties = {"spring.json.value.default.type=banking.model.dto.CardCreateRequest"}
    )
    public void handleCardCreate(CardCreateRequest request) {
        System.out.println("Message, create card: " + request);

        String status = request.getStatus();
        if ("CLOSED".equalsIgnoreCase(status) ||
                "FROZEN".equalsIgnoreCase(status) ||
                "ARRESTED".equalsIgnoreCase(status)) {
            throw new IllegalStateException("Can't create card with status: " + status);
        }

        Account account = accountRepository.findById(request.getAccount_id())
                .orElseThrow(() -> new RuntimeException("Account not found: " + request.getAccount_id()));

        Card card = new Card();
        card.setAccount(account);
        card.setCardId(generateUniqueCardNumber());
        card.setPaymentSystem(request.getPaymentSystem());
        card.setStatus(Status.valueOf(request.getStatus()));

        cardRepository.save(card);
        System.out.println("Card for account " + account.getId() + ", created, cardId: " + card.getCardId());
    }

    private String generateUniqueCardNumber() {
        String cardNumber;
        do {
            cardNumber = generateCardNumber();
        } while (cardRepository.existsByCardId(cardNumber));
        return cardNumber;
    }

    private String generateCardNumber() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }

}
