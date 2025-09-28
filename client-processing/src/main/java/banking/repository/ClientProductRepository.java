package banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import banking.model.ClientProduct;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {
    List<ClientProduct> findByClientId(Long clientId);
}
