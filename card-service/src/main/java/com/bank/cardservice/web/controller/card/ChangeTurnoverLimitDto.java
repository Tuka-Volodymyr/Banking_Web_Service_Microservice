package com.bank.cardservice.web.controller.card;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangeTurnoverLimitDto {

  @NotBlank
  private float limitOfTurnover;
  @Email
  @Pattern(regexp = ".+\\.com")
  private String email;
}
