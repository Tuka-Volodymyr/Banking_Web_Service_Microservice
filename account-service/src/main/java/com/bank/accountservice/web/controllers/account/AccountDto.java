package com.bank.accountservice.web.controllers.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    @NotBlank
    private String fullName;
    @Email
    @Pattern(regexp = ".+\\.com")
    private String email;
    @Size(min = 4)
    private String password;
}
