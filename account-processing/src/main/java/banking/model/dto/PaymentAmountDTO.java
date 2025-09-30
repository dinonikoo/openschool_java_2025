package banking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentAmountDTO {
    private Long accountId;
    private Double amount; // сумма платежа на конкретную дату
}
