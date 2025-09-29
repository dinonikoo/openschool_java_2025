package banking.model.dto;

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
public class ClientProductRequestDTO {
    @JsonProperty("client_id")
    private Long clientId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("credit_amount")
    private Double creditAmount;

    @JsonProperty("month_count")
    private Integer monthCount;

    @JsonProperty("annual_rate")
    private Double annualRate;
}
