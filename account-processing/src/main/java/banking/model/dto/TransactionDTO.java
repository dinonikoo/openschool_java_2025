package banking.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionDTO {
    private UUID id;
    private Long accountId;
    private String cardId;
    private String type;          // DEPOSIT / WITHDRAW
    private Double amount;
    private String status;        // ALLOWED, PROCESSING, COMPLETE, BLOCKED, CANCELLED
    private LocalDateTime timestamp;
}
