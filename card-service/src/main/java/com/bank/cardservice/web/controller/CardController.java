package com.bank.cardservice.web.controller;

import com.bank.cardservice.service.CardService;
import com.bank.cardservice.web.controller.card.ChangeTurnoverLimitDto;
import com.bank.cardservice.web.controller.card.MoneyToCardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CardController {

  private final CardService cardService;

  @GetMapping("/add")
  public ResponseEntity<?> addCard(@RequestParam("email") String email) {
    return new ResponseEntity<>(cardService.addCreditCard(email), HttpStatus.CREATED);
  }

  @GetMapping("/info")
  public ResponseEntity<?> getInfoAboutCard(@RequestParam("email") String email) {
    return new ResponseEntity<>(cardService.getInfoAboutCard(email), HttpStatus.OK);
  }

  @PostMapping("/balance/add/money")
  public ResponseEntity<?> addMoney(@RequestBody MoneyToCardDto moneyToCardDto) {
    return new ResponseEntity<>(cardService.addMoney(moneyToCardDto), HttpStatus.OK);
  }

  @PostMapping("/balance/transfer/money")
  public ResponseEntity<?> transferMoney(@RequestBody MoneyToCardDto moneyToCardDto,
      @RequestParam("email") String email) {
    return new ResponseEntity<>(cardService.transferMoney(moneyToCardDto, email), HttpStatus.OK);
  }

  @GetMapping("/lock")
  public ResponseEntity<?> lockCard(@RequestParam("email") String email) {
    return new ResponseEntity<>(cardService.lockCard(email), HttpStatus.LOCKED);
  }

  @GetMapping("/unlock")
  public ResponseEntity<?> unlockCard(@RequestParam("email") String email) {
    return new ResponseEntity<>(cardService.unlockCard(email), HttpStatus.OK);
  }

  @PostMapping("/change/limitOfTurnover")
  public ResponseEntity<?> changeMaxTurnover(
      @RequestBody ChangeTurnoverLimitDto changeTurnoverLimitDto) {
    return new ResponseEntity<>(cardService.newTurnoverLimit(changeTurnoverLimitDto),
        HttpStatus.OK);
  }
}
