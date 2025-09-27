/*

BlacklistRegistry:
- documentType
- documentId
- blacklisted_at
- reason
- blacklist_expiration_date

 */

package banking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "blacklist_registry")
@Data
public class BlacklistRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    private String documentId;

    private LocalDate blacklistedAt;

    private String reason;
    private LocalDate blacklistExpirationDate;
}
