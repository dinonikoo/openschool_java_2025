package banking.service;

import banking.model.ClientProduct;
import banking.model.Product;
import banking.repository.ClientProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientProductService {

    private final ClientProductRepository clientProductRepository;

    @Transactional
    public ClientProduct createClientProduct(ClientProduct clientProduct) {
        clientProduct.setOpenDate(LocalDateTime.now());
        return clientProductRepository.save(clientProduct);
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
