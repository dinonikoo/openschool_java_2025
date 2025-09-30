package banking.utils;

import aj.org.objectweb.asm.commons.GeneratorAdapter;
import banking.model.Transaction;
import banking.model.TransactionStatus;
import banking.model.Type;
import banking.model.dto.TransactionDTO;
import banking.repository.AccountRepository;

import banking.repository.CardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@AllArgsConstructor
public class TransactionMapper {
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    public Transaction fromMessage(TransactionDTO msg) {
        return Transaction.builder()
                .account(accountRepository.findById(msg.getAccountId())
                        .orElseThrow(() -> new IllegalArgumentException("Account not found: " + msg.getAccountId())))
                .card(cardRepository.findByCardId(msg.getCardId())
                        .orElseThrow(() -> new IllegalArgumentException("Card not found: " + msg.getAccountId())))
                .type(Type.valueOf(msg.getType()))
                .amount(msg.getAmount())
                .status(TransactionStatus.valueOf(msg.getStatus()))
                .timestamp(msg.getTimestamp())
                .build();
    }
}
