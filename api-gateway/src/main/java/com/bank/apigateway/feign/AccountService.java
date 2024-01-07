package com.bank.apigateway.feign;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ACCOUNT-SERVICE")
public interface AccountService {

    @GetMapping("/account/get/userDetails")
    ResponseEntity<Map<String, String>> getUserDetails(@RequestParam("email") String email);
}
