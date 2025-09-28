package banking.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private LocalDate paymentDate;
    private Double amount;
    private Boolean isCredit;

    private LocalDateTime payedAt;
    @Enumerated(EnumType.STRING)
    private Type type;
}

