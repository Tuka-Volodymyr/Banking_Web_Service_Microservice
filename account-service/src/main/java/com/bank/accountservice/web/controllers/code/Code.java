package com.bank.accountservice.web.controllers.code;

import java.util.Random;
import lombok.Data;

@Data
public class Code {

  private String code;

  public Code() {
    code = generateCod();
  }

  public String generateCod() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder code = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      int randomIndex = random.nextInt(characters.length());
      char randomChar = characters.charAt(randomIndex);
      code.append(randomChar);
    }
    return code.toString();
  }
}
