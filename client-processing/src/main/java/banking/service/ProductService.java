package banking.service;

import banking.model.Product;
import banking.model.ProductKey;
import banking.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        LocalDateTime now = LocalDateTime.now();
        product.setProductId(now.toString());
        product.setCreateDate(now);

        Product saved = productRepository.save(product);

        saved.setProductId(String.valueOf(saved.getKey()) + saved.getId());

        return productRepository.save(saved);

        //product.setProductId(String.valueOf(product.getKey()) + product.getId()); //
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setKey(updatedProduct.getKey());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
