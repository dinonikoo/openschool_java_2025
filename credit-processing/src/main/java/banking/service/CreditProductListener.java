package banking.service;


import banking.model.dto.CreditRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditProductListener {

    private final CreditProcessingService creditProcessingService;

    @KafkaListener(topics = "client_credit_products", groupId = "credit-group")
    public void handleCreditRequest(CreditRequestDTO request) {
        creditProcessingService.processCreditRequest(request);
    }
}
