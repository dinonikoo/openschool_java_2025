package banking.controller;

import banking.model.dto.CardCreateRequest;
import banking.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/create")
    public String requestCard(@RequestBody CardCreateRequest request) {
        cardService.sendCardCreateRequest(request);
        return "Запрос на создание карты отправлен";
    }
}
