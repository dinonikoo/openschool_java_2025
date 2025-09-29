package banking.controller;

import banking.model.User;
import banking.model.Client;
import banking.model.dto.ClientInfoDTO;
import banking.model.dto.ClientRegistrationDTO;
import banking.service.ClientService;
import banking.utils.ClientRegistrationMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientRegistrationMapper mapper;

    @PostMapping("/register")
    public ClientRegistrationDTO registerClient(@RequestBody ClientRegistrationDTO dto) {
        User user = mapper.toUser(dto);
        Client client = mapper.toEntity(dto, user);

        String regionCode = "77";  // пример
        String branchCode = "01";  // пример

        Client savedClient = clientService.registerClient(regionCode, branchCode, user, client);
        return mapper.toDto(savedClient);
    }

    @GetMapping("/{id}")
    public ClientInfoDTO getClientInfo(@PathVariable Long id) {
        Client client = clientService.findById(id);
        ClientInfoDTO dto = new ClientInfoDTO();
        dto.setFirstName(client.getFirstName());
        dto.setMiddleName(client.getMiddleName());
        dto.setLastName(client.getLastName());
        dto.setDocumentType(client.getDocumentType().name());
        dto.setDocumentId(client.getDocumentId());
        return dto;
    }

}
