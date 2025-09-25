package banking.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "payment_registry")
@Data
public class PaymentRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_registry_id", nullable = false)
    private ProductRegistry productRegistry;

    private LocalDate paymendDate;
    private Double amount;
    private Double interestRateAmount;
    private Double debtAmount;
    private Boolean expired;
    private LocalDate paymentExpirationDate;
}
