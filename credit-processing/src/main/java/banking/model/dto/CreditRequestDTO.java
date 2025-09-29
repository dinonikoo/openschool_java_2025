package banking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestDTO {
    private Long id;
    private Long productId;
    private Long clientId;
    private String productKey;
    private String status;
    private LocalDateTime openDate;

    private Double creditAmount;
    private Integer monthCount;
    private Double annualRate;
}
