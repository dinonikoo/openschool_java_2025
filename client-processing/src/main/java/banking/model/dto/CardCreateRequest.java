package banking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CardCreateRequest {
    @JsonProperty("account_id")
    private Long account_id;

    @JsonProperty("payment_system")
    private String paymentSystem;

    //@JsonProperty("status")
    //private String status;
}
