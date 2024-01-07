package com.bank.cardservice.feign;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("EVENT-SERVICE")
public interface EventInterface {

  @GetMapping("/event/card/create")
  ResponseEntity<?> createCard(@RequestParam("email") String email);

  @PostMapping("/event/card/add/money")
  ResponseEntity<?> addMoney(@RequestBody Map<String,String> card,
      @RequestParam("suma") float suma);

  @PostMapping("/event/card/transaction")
  ResponseEntity<?> transaction(@RequestBody Map<String, String> cards,
      @RequestParam("email") float suma);

  @GetMapping("/event/card/lock")
  ResponseEntity<?> lockCard(@RequestBody String email);

  @GetMapping("/event/card/unlock/card")
  ResponseEntity<?> unlockCard(@RequestBody String email);

  @GetMapping("/event/card/limit/turnover")
  ResponseEntity<?> newLimitOfTurnover(@RequestBody String email);
}
