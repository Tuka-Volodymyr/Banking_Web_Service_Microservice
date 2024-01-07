package com.bank.accountservice.web.controllers;

import com.bank.accountservice.service.AccountService;
import com.bank.accountservice.web.controllers.account.AccountDto;
import com.bank.accountservice.web.controllers.account.PasswordDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final HttpSession session;


    @PostMapping("/add")
    public ResponseEntity<?> addAccount(@Valid @RequestBody AccountDto account) {
        return new ResponseEntity<>(accountService.addAccount(account), HttpStatus.CREATED);
    }

    @PostMapping("/lock")
    public ResponseEntity<?> lockAccount(@RequestParam String email) {
        return new ResponseEntity<>(accountService.lockAccount(email), HttpStatus.LOCKED);
    }

    @PostMapping("/unlock")
    public ResponseEntity<?> unlockAccount(@RequestParam String email) {
        return new ResponseEntity<>(accountService.unlockAccount(email), HttpStatus.OK);
    }

    @PostMapping("/change/password/send/code")
    public ResponseEntity<?> sendCodeForChangePassword(@RequestParam("email") String email) {
        accountService.sendCodeToEmailForChangePassword(email, session);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change/password/check/code")
    public ResponseEntity<?> checkCodeForChangePassword(@RequestParam("code") String code) {
        accountService.checkCodeForChangePassword(code, session);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordDto passwordDto) {
        accountService.changePassword(passwordDto, session);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/userDetails")
    public ResponseEntity<Map<String, String>> getUserDetails(@RequestParam("email") String email) {
        return new ResponseEntity<>(accountService.getUserDetails(email), HttpStatus.OK);
    }

}
