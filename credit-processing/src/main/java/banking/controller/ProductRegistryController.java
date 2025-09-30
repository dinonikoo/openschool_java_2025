package banking.controller;

import banking.model.PaymentRegistry;
import banking.model.ProductRegistry;
import banking.model.dto.AccountInfoDTO;
import banking.model.dto.ClientInfoDTO;
import banking.model.dto.PaymentAmountDTO;
import banking.repository.PaymentRegistryRepository;
import banking.repository.ProductRegistryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productRegistry")
@RequiredArgsConstructor
public class ProductRegistryController {

    private final ProductRegistryRepository productRegistryRepository;
    private final PaymentRegistryRepository paymentRegistryRepository;

    @GetMapping("/{accountId}")
    public AccountInfoDTO getProductInfo(@PathVariable("accountId") Long accountId) {
        ProductRegistry creditProduct = productRegistryRepository.
                findByAccountId(accountId).orElseThrow(() -> new IllegalArgumentException("ProductRegistry not found"));
        List<PaymentRegistry> pendingPayments = paymentRegistryRepository
                .findByProductRegistryInAndPaymendDateIsNull(creditProduct);
        double totalDebt = pendingPayments.stream()
                .mapToDouble(PaymentRegistry::getAmount)
                .sum();
        return new AccountInfoDTO(accountId, totalDebt);
    }
    @GetMapping("/{accountId}/payment")
    public PaymentAmountDTO getPaymentForDate(
            @PathVariable("accountId") Long accountId,
            @RequestParam("paymentDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate paymentDate) {

            ProductRegistry creditProduct = productRegistryRepository.
                findByAccountId(accountId).orElseThrow(() -> new IllegalArgumentException("ProductRegistry not found"));

            if (creditProduct == null) {
                return new PaymentAmountDTO(accountId, 0.0);
            }

            PaymentRegistry payment = paymentRegistryRepository
                    .findByProductIdAndPaymentExpirationDateAndPaymendDateIsNull(creditProduct.getId(), paymentDate)
                    .orElseThrow(() -> new IllegalArgumentException("PaymentRegistry not found"));

            double amount = (payment != null) ? payment.getAmount() : 0.0;

            return new PaymentAmountDTO(accountId, amount);
        }
}


