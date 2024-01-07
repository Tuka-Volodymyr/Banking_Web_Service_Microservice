package com.bank.accountservice.feign;

import com.bank.accountservice.model.entities.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("EVENT-SERVICE")
public interface EventInterface {

    @GetMapping("/event/account/create")
    ResponseEntity<?> createAccount(@RequestParam("email") String email);

    @GetMapping("/event/account/change/password")
    ResponseEntity<?> changePassword(@RequestParam("email") String email);

    @GetMapping("/event/account/lock")
    ResponseEntity<?> lockAccount(@RequestBody String email);

    @GetMapping("/event/account/unlock")
    ResponseEntity<?> unlockAccount(@RequestBody String email);
}
