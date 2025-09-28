package banking.model.dto;

import banking.model.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ClientRegistrationDTO {

    // User
    @JsonProperty("login")
    private String login;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    // Client
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("document_prefix")
    private String documentPrefix;

    @JsonProperty("document_suffix")
    private String documentSuffix;
}
