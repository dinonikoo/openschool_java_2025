package banking.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentDTO {
    private UUID id;
    private Long accountId;
    private Double amount;
    private LocalDateTime paymentDate;
}
