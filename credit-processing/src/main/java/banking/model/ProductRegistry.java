package banking.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "product_registry")
@Data
public class ProductRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;
    private Long accountId;
    private Long productId;
    private Double interestRate;
    private LocalDate openDate;

}
