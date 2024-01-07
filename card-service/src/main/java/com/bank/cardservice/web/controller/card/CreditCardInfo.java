package com.bank.cardservice.web.controller.card;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditCardInfo {

  private String card;
  private String balance;
}
