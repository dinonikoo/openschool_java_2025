package banking.service;


import banking.model.PaymentRegistry;
import banking.model.ProductRegistry;
import banking.model.dto.CreditRequestDTO;
import banking.repository.PaymentRegistryRepository;
import banking.repository.ProductRegistryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditProductListener {

    private final CreditProcessingService creditProcessingService;
    private final ProductRegistryRepository productRegistryRepository;
    private final PaymentRegistryRepository paymentRegistryRepository;

    @KafkaListener(topics = "client_credit_products", groupId = "credit-group")
    public void handleCreditRequest(CreditRequestDTO request) {
        creditProcessingService.processCreditRequest(request);
    }

    @KafkaListener(
            topics = "client_credit_paid",
            groupId = "credit-group"
    )
    public void handleCreditFullyPaid(String message) {
        Long accountId = extractAccountId(message);
        if (accountId == null) return;

        ProductRegistry product = productRegistryRepository.findByAccountId(accountId).
                orElseThrow(() -> new RuntimeException("ProductRegistry not found"));

        if (product == null) return;

        List<PaymentRegistry> pendingPayments = paymentRegistryRepository
                .findByProductIdAndPaymentDateIsNull(product.getId());

        // 3. Отмечаем их как оплаченные
        LocalDateTime now = LocalDateTime.now();
        for (PaymentRegistry p : pendingPayments) {
            p.setPaymendDate(LocalDate.from(now));
        }

        paymentRegistryRepository.saveAll(pendingPayments);
    }

    private Long extractAccountId(String message) {
        try {
            String[] parts = message.split(":");
            return Long.parseLong(parts[1].trim());
         } catch (NumberFormatException e) {
        return null;
        }
    }
}
