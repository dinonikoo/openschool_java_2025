package banking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfoDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String documentType;
    private String documentId;
}
