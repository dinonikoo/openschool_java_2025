package banking.utils;

import banking.model.BlacklistRegistry;
import banking.model.DocumentType;
import banking.model.dto.BlacklistDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class BlacklistMapper {

    public BlacklistRegistry toEntity(BlacklistDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("BlacklistDTO is null");
        }

        return BlacklistRegistry.builder()
                .documentType(DocumentType.valueOf(dto.getDocumentType()))
                .documentId(dto.getDocumentId())
                .blacklistedAt(dto.getBlacklistedAt())
                .reason(dto.getReason())
                .blacklistExpirationDate(dto.getBlacklistExpirationDate())
                .build();
    }
    /*
      BlacklistRegistry -> DTO (без User)
     */
    public BlacklistDTO toDto(BlacklistRegistry entity) {
        if (entity == null) {
            return null;
        }

        return BlacklistDTO.builder()
                .documentType(String.valueOf(entity.getDocumentType()))
                .documentId(entity.getDocumentId())
                .blacklistedAt(entity.getBlacklistedAt())
                .reason(entity.getReason())
                .blacklistExpirationDate(entity.getBlacklistExpirationDate())
                .build();
    }
}
