package banking.repository;

import banking.model.ProductRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRegistryRepository extends JpaRepository<ProductRegistry, Long> {
    Optional<ProductRegistry> findByAccountId(Long id);
}