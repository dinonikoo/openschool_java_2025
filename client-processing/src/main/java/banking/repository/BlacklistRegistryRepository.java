package banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import banking.model.BlacklistRegistry;
import banking.model.DocumentType;
import java.util.Optional;


@Repository
public interface BlacklistRegistryRepository extends JpaRepository<BlacklistRegistry, Long> {
    boolean existsByDocumentTypeAndDocumentId(DocumentType type, String documentId);
    Optional<BlacklistRegistry> findByDocumentTypeAndDocumentId(DocumentType tyoe, String documentId);
}
