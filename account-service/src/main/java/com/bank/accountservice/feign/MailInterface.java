package com.bank.accountservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("MAIL-SERVICE")
public interface MailInterface {

    @PostMapping("/mail/change/password/send/code")
    ResponseEntity<?> sendCodeForChangePassword
        (@RequestParam("email") String email, @RequestParam("code") String code);
}
