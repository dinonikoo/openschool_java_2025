package banking.service;

import banking.model.ClientProduct;
import banking.model.Product;
import banking.model.ProductKey;
import banking.model.dto.ClientProductResponse;
import banking.repository.ClientProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ClientProductService {

    private final ClientProductRepository clientProductRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public ClientProduct createClientProduct(ClientProduct clientProduct) throws ExecutionException, InterruptedException {
        clientProduct.setOpenDate(LocalDateTime.now());
        ClientProduct saved = clientProductRepository.save(clientProduct);

        ClientProductResponse event = new ClientProductResponse(
                saved.getId(),
                saved.getProduct().getId(),
                saved.getClient().getId(),
                String.valueOf(saved.getProduct().getKey()),
                String.valueOf(saved.getStatus()),
                saved.getOpenDate()
        );

        String topic = switch (clientProduct.getProduct().getKey()) {
            case DC, CC, NS, PENS -> "client_products";
            case IPO, PC, AC -> "client_credit_products";
            default -> "client_products";
        };

        kafkaTemplate.send(topic, event).get();

        return saved;
    }

    public List<ClientProduct> getAllClientProducts() {
        return clientProductRepository.findAll();
    }

    public Optional<ClientProduct> getClientProduct(Long id) {
        return clientProductRepository.findById(id);
    }
    @Transactional
    public ClientProduct updateClientProduct(Long id, ClientProduct updatedClientProduct) {
        return clientProductRepository.findById(id).map(product -> {
            product.setProduct(updatedClientProduct.getProduct());
            product.setClient(updatedClientProduct.getClient());
            //product.setOpenDate(updatedClientProduct.getOpenDate());
            product.setStatus(updatedClientProduct.getStatus());
            product.setCloseDate(updatedClientProduct.getCloseDate());
            return clientProductRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("ClientProduct not found"));
    }
    @Transactional
    public void deleteClientProduct(Long id) {
        clientProductRepository.deleteById(id);
    }
}
