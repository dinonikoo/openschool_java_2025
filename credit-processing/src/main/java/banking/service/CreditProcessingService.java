package banking.service;

import banking.model.dto.ClientInfoDTO;
import banking.utils.ClientGateway;
import banking.utils.CreditScheduleCalculator;
import banking.model.PaymentRegistry;
import banking.model.ProductRegistry;
import banking.model.dto.CreditRequestDTO;
import banking.repository.PaymentRegistryRepository;
import banking.repository.ProductRegistryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditProcessingService {

    private final ProductRegistryRepository productRegistryRepository;
    private final PaymentRegistryRepository paymentRegistryRepository;
    private final ClientGateway clientGateway;

    @Value("${credit.limit}")
    private Double creditLimit;

    @Transactional
    public void processCreditRequest(CreditRequestDTO dto) {
        ClientInfoDTO clientInfo = clientGateway.getClientInfo(dto.getClientId());

        Double currentDebt = sumDebtByClientId(dto.getClientId());
        if (currentDebt + dto.getCreditAmount() > creditLimit) {
            dto.setStatus("CLOSED");
            return;
        }

        boolean hasExpired = paymentRegistryRepository.existsByProductRegistryClientIdAndExpiredTrue(dto.getClientId());
        if (hasExpired) {
            dto.setStatus("CLOSED");
            return;
        }

        openCreditProduct(dto);
    }

    private void openCreditProduct(CreditRequestDTO dto) {
        ProductRegistry registry = new ProductRegistry();
        registry.setClientId(dto.getClientId());
        registry.setProductId(dto.getProductId());
        registry.setInterestRate(dto.getAnnualRate());
        registry.setOpenDate(LocalDate.from(dto.getOpenDate()));
        registry.setMonthCount(dto.getMonthCount());
        productRegistryRepository.save(registry);

        List<PaymentRegistry> schedule = CreditScheduleCalculator.buildSchedule(
                dto.getCreditAmount(),
                dto.getAnnualRate(),
                dto.getMonthCount(),
                registry
        );
        paymentRegistryRepository.saveAll(schedule);
    }

    Double sumDebtByClientId(Long clientId) {
        return paymentRegistryRepository.findByProductRegistryClientId(clientId)
                .stream()
                .mapToDouble(PaymentRegistry::getDebtAmount)
                .sum();
    }


}
