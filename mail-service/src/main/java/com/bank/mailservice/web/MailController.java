package com.bank.mailservice.web;

import com.bank.mailservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/change/password/send/code")
    public ResponseEntity<?> sendCodeForChangePassword(@RequestParam("email") String email,
        @RequestParam("code") String code) {
        mailService.sendCodeToEmailForChangePassword(email, code);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
