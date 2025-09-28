package banking.service;

import banking.model.BlacklistRegistry;
import banking.model.Client;
import banking.model.ClientSequence;
import banking.model.User;
import banking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import banking.utils.ClientRegistrationMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlacklistService {
    private final BlacklistRegistryRepository blacklistRepository;

    @Transactional
    public void addToBlacklist(BlacklistRegistry entry) {
        if (!blacklistRepository.existsByDocumentTypeAndDocumentId(entry.getDocumentType(), entry.getDocumentId())) {
            blacklistRepository.save(entry);
        }
    }
}
