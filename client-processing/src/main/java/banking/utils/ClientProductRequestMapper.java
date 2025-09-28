package banking.utils;

import banking.model.ClientProductStatus;
import banking.repository.ClientRepository;
import banking.repository.ProductRepository;
import banking.model.Client;
import banking.model.Product;
import banking.model.ClientProduct;
import banking.model.dto.ClientProductRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientProductRequestMapper {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public ClientProduct toEntity(ClientProductRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ClientProductRequestDTO is null");
        }

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return ClientProduct.builder()
                .client(client)
                .product(product)
                .status(ClientProductStatus.valueOf(dto.getStatus()))
                .build();
    }
}