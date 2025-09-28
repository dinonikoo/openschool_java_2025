package banking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.*;

@Entity
@Table(name = "client_products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDateTime openDate;
    private LocalDateTime closeDate;

    @Enumerated(EnumType.STRING)
    private ClientProductStatus status;
}
