package banking.repository;

import banking.model.PaymentRegistry;
import banking.model.ProductRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRegistryRepository extends JpaRepository<PaymentRegistry, Long> {
    List<PaymentRegistry> findByProductRegistryClientId(Long clientId);
    boolean existsByProductRegistryClientIdAndExpiredTrue(Long clientId);

    List<PaymentRegistry> findByProductRegistryInAndPaymendDateIsNull(ProductRegistry productRegistry);

    List<PaymentRegistry> findByProductIdAndPaymentDateIsNull(Long id);

    Optional<PaymentRegistry> findByProductIdAndPaymentExpirationDateAndPaymendDateIsNull(Long id, LocalDate paymentDate);
}