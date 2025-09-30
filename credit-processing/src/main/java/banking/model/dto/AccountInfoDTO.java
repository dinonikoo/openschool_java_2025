package banking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountInfoDTO {
    private Long accountId;
    private Double totalDebt; // суммарная задолженность
}
