package com.bank.eventservice.web.controllers;


import com.bank.eventservice.services.EventService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/card")
public class EventCardController {

    private final EventService eventService;

    @GetMapping("/history")
    public ResponseEntity<?> getHistoryTransaction(@RequestParam("email") String email) {
        eventService.getOperationEvents(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/create")
    public ResponseEntity<?> createCard(@RequestParam("email") String email) {
        eventService.createCard(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add/money")
    public ResponseEntity<?> addMoney(@RequestBody Map<String,String> card,
        @RequestParam("suma") float suma) {
        eventService.addMoney(card, suma);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(@RequestBody Map<String, String> cards,
        @RequestParam("email") float suma) {
        eventService.transaction(cards,suma);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/lock")
    public ResponseEntity<?> lockCard(@RequestBody String email) {
        eventService.lockCard(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/unlock")
    public ResponseEntity<?> unlockCard(@RequestBody String email) {
        eventService.unlockCard(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/limit/turnover")
    public ResponseEntity<?> newLimitOfTurnover(@RequestBody String email) {
        eventService.newLimitOfTurnover(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
