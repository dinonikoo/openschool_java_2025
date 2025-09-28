package banking.controller;

import banking.model.BlacklistRegistry;
import banking.service.BlacklistService;
import banking.utils.BlacklistMapper;
import banking.model.dto.BlacklistDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blacklist")
@AllArgsConstructor
public class BlacklistController {

    private final BlacklistMapper mapper;
    private final BlacklistService blacklistService;

    @PostMapping("/add")
    public ResponseEntity<String> addToBlacklist(@RequestBody BlacklistDTO dto) {
        BlacklistRegistry blacklistReg = mapper.toEntity(dto);
        blacklistService.addToBlacklist(blacklistReg);
        return ResponseEntity.ok("Added to blacklist");
    }
}
