package banking.controller;

import banking.model.ClientProduct;
import banking.model.dto.ClientProductRequestDTO;
import banking.service.ClientProductService;
import banking.utils.ClientProductRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/client-products")
@RequiredArgsConstructor
public class ClientProductController {

    private final ClientProductService clientProductService;
    private final ClientProductRequestMapper mapper;

    @PostMapping
    public ResponseEntity<ClientProduct> createClientProduct(@RequestBody ClientProductRequestDTO dto) throws ExecutionException, InterruptedException {
       /* if (dto.getClientId() == null) {
            throw new IllegalArgumentException("clientId must not be null");
        }
        if (dto.getProductId() == null) {
            throw new IllegalArgumentException("productId must not be null");
        }*/

        return ResponseEntity.ok(clientProductService.createClientProduct(mapper.toEntity(dto)));
    }

    @GetMapping
    public ResponseEntity<List<ClientProduct>> getAllClientProducts() {
        return ResponseEntity.ok(clientProductService.getAllClientProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientProduct> getClientProduct(@PathVariable Long id) {
        return clientProductService.getClientProduct(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientProduct> updateClientProduct(@PathVariable Long id, @RequestBody ClientProductRequestDTO dto) {
        return ResponseEntity.ok(clientProductService.updateClientProduct(id, mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientProduct(@PathVariable Long id) {
        clientProductService.deleteClientProduct(id);
        return ResponseEntity.noContent().build();
    }
}
