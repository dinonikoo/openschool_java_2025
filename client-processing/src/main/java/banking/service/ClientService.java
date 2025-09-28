package banking.service;

import banking.model.Client;
import banking.model.ClientSequence;
import banking.model.User;
import banking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import banking.utils.ClientRegistrationMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final BlacklistRegistryRepository blacklistRegistryRepository;
    private final ClientRegistrationMapper mapper;
    private final ClientSequenceRepository sequenceRepository;

    @Transactional
    public Client registerClient(String regionCode, String branchCode, User user, Client client) {

        blacklistRegistryRepository
                .findByDocumentTypeAndDocumentId(client.getDocumentType(), client.getDocumentId())
                .ifPresent(entry -> {
                    // сравниваем дату
                    if (entry.getBlacklistExpirationDate() == null || entry.getBlacklistExpirationDate().isAfter(LocalDate.now())) {
                        throw new IllegalStateException("Client is blacklisted until " + entry.getBlacklistExpirationDate());
                    }
                });

        if (user.getId() == null) {
            user = userRepository.save(user);
        }

        String clientId = generateClientId(regionCode, branchCode);
        client.setClientId(clientId);
        client.setUser(user);

        return clientRepository.save(client);
    }



    @Transactional
    public String generateClientId(String region, String branch) {
        ClientSequence seq = sequenceRepository
                .findByRegionCodeAndBranchCode(region, branch)
                .orElseGet(() -> new ClientSequence(region, branch, 0L));

        long nextNumber = seq.getLastNumber() + 1;
        seq.setLastNumber(nextNumber);

        sequenceRepository.save(seq);

        return String.format("%s%s%08d", region, branch, nextNumber);
    }
}
