package banking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*
BlacklistRegistry {
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
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class BlacklistDTO {

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("blacklisted_at")
    private LocalDate blacklistedAt;

    @JsonProperty("blacklist_expiration_date")
    private LocalDate blacklistExpirationDate;

}
