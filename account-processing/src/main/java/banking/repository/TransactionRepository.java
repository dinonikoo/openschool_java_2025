package banking.repository;

import banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT COUNT(t) FROM Transaction t " +
            "WHERE t.cardId = :cardId " +
            "AND t.timestamp >= :from")
    long countRecentTransactions(@Param("cardId") String cardId,
                                 @Param("from") LocalDateTime from);
}