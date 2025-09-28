package banking.repository;

import banking.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByCardId(String cardNumber);
}