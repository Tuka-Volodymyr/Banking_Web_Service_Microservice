package com.bank.cardservice.web.controller.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyToCardDto {

  @Size(min = 16, max = 16)
  private String card;
  @NotBlank
  private float suma;
}
