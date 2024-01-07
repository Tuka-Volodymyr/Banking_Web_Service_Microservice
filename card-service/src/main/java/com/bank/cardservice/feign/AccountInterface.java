package com.bank.cardservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@FeignClient("ACCOUNT-SERVICE")
public interface AccountInterface {

  @GetMapping("/account/get/userDetails")
  ResponseEntity<Map<String, String>> getUserDetails(@RequestParam("email") String email);

  @PostMapping("/account/lock")
  ResponseEntity<?> lockAccount(@RequestParam("email") String email);
}
