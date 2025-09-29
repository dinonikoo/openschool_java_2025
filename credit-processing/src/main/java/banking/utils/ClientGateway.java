package banking.utils;

import banking.model.dto.ClientInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ClientGateway {

    private final RestTemplate restTemplate;

    @Value("${client-processing.url:http://localhost:8080/api}")
    private String baseUrl;

    public ClientInfoDTO getClientInfo(Long clientId) {
        return restTemplate.getForObject(
                baseUrl + "/clients/{id}",
                ClientInfoDTO.class,
                clientId
        );
    }
}
