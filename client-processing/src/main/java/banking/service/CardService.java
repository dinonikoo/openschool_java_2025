package banking.service;

import banking.model.dto.CardCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCardCreateRequest(CardCreateRequest request) {
        kafkaTemplate.send("client_cards", request);
        System.out.println("Sending card creating request: " + request);
    }
}
