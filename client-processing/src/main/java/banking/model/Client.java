package banking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String clientId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String documentId;
    private String documentPrefix;
    private String documentSuffix;
}
