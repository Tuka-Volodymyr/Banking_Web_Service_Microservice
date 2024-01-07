package com.bank.eventservice.web.controllers;

import com.bank.eventservice.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/account")
public class EventAccountController {

    private final EventService eventService;

    @GetMapping("/get")
    public ResponseEntity<?> getGlobalEvents() {
        eventService.getGlobalEvents();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/create")
    public ResponseEntity<?> createAccount(@RequestParam("email") String email) {
        eventService.createAccount(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/change/password")
    public ResponseEntity<?> changePassword(@RequestParam("email") String email) {
        eventService.changePassword(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/lock")
    public ResponseEntity<?> lockAccount(@RequestBody String email) {
        eventService.lockAccount(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/unlock")
    public ResponseEntity<?> unlockAccount(@RequestBody String email) {
        eventService.unlockAccount(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
