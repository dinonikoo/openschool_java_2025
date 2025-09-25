package banking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "card")
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private String cardId;
    private String paymentSystem;
    private String status;
}
