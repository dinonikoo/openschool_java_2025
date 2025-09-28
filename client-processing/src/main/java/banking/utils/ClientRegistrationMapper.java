package banking.utils;

import banking.model.Client;
import banking.model.DocumentType;
import banking.model.User;
import banking.model.dto.ClientRegistrationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class ClientRegistrationMapper {

    /*
     DTO -> Client + User
     */
    public Client toEntity(ClientRegistrationDTO dto, User user) {
        if (dto == null) {
            throw new IllegalArgumentException("ClientRegistrationDTO is null");
        }

        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .dateOfBirth(LocalDate.parse(dto.getDateOfBirth()))
                .documentType(DocumentType.valueOf(dto.getDocumentType()))
                .documentId(dto.getDocumentId())
                .documentPrefix(dto.getDocumentPrefix())
                .documentSuffix(dto.getDocumentSuffix())
                .user(user)
                .build();
    }

    /*
     DTO -> User
     */
    public User toUser(ClientRegistrationDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ClientRegistrationDTO is null");
        }

        return User.builder()
                .login(dto.getLogin())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    /*
      Client -> DTO (без User)
     */
    public ClientRegistrationDTO toDto(Client entity) {
        if (entity == null) {
            return null;
        }

        return ClientRegistrationDTO.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .dateOfBirth(String.valueOf(entity.getDateOfBirth()))
                .documentType(String.valueOf(entity.getDocumentType()))
                .documentId(entity.getDocumentId())
                .documentPrefix(entity.getDocumentPrefix())
                .documentSuffix(entity.getDocumentSuffix())
                .email(entity.getUser() != null ? entity.getUser().getEmail() : null)
                .login(entity.getUser() != null ? entity.getUser().getLogin() : null)
                .build();
    }
}
