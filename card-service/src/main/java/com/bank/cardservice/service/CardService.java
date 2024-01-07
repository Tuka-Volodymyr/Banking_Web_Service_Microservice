package com.bank.cardservice.service;

import com.bank.cardservice.model.entity.CreditCard;
import com.bank.cardservice.web.controller.card.ChangeTurnoverLimitDto;
import com.bank.cardservice.web.controller.card.CreditCardInfo;
import com.bank.cardservice.web.controller.card.MoneyToCardDto;
import org.springframework.transaction.annotation.Transactional;

public interface CardService {

  void save(CreditCard creditCard);

  CreditCard findCardByEmail(String emailOfOwner);

  CreditCard findCardByCard(String card);

  void existCard(String email);

  CreditCardInfo addCreditCard(String email);

  CreditCard getInfoAboutCard(String email);

  CreditCardInfo addMoney(MoneyToCardDto moneyToCardDto);

  @Transactional
  void addMoneyTransaction(float amount, CreditCard creditCard);

  CreditCardInfo transferMoney(MoneyToCardDto moneyToCardDto, String email);

  @Transactional
  void transferMoneyTransaction(float amount, CreditCard cardOfSubject, CreditCard cardOfObject);

  void checkTurnover(String email);

  String newTurnoverLimit(ChangeTurnoverLimitDto changeTurnoverLimitDto);

  void cardIsLock(CreditCard creditCard);

  String lockCard(String email);

  String unlockCard(String email);
}
