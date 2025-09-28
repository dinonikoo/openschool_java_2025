package banking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@Table(name = "client_sequence")
@NoArgsConstructor
@AllArgsConstructor
public class ClientSequence {

    @Id
    private String regionCode;

    private String branchCode;

    private Long lastNumber;
}
