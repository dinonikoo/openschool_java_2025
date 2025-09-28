package banking.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;
    private Long productId;
    private Double balance;
    private Double interestRate;
    private Boolean isRecalc;
    private Boolean cardExist;
    @Enumerated(EnumType.STRING)
    private Status status;
}
