package banking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductKey key;

    private LocalDateTime createDate;

    @Column(nullable = false, unique = true)
    private String productId; // key + id
}
