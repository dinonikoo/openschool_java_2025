package banking.repository;

import banking.model.ClientSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientSequenceRepository extends JpaRepository<ClientSequence, String> {
    Optional<ClientSequence> findByRegionCodeAndBranchCode(String regionCode, String branchCode);
}
