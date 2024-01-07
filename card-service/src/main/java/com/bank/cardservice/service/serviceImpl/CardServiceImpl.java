package com.bank.cardservice.service.serviceImpl;


import com.bank.cardservice.feign.AccountInterface;
import com.bank.cardservice.feign.EventInterface;
import com.bank.cardservice.model.entity.CreditCard;
import com.bank.cardservice.model.entity.exception.BadRequestException;
import com.bank.cardservice.model.entity.exception.CardIsLockException;
import com.bank.cardservice.model.entity.exception.CreditCardNotFoundException;
import com.bank.cardservice.repositories.CreditCardInfoRepository;
import com.bank.cardservice.service.CardService;
import com.bank.cardservice.web.controller.card.ChangeTurnoverLimitDto;
import com.bank.cardservice.web.controller.card.CreditCardInfo;
import com.bank.cardservice.web.controller.card.MoneyToCardDto;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {


  private final AccountInterface accountInterface;

  private final CreditCardInfoRepository creditCardInfoRepository;

  private final EventInterface eventInterface;

  @Override
  public void save(CreditCard creditCard) {
    if (creditCard != null) {
      creditCardInfoRepository.save(creditCard);
    } else {
      throw new NullPointerException();
    }
  }

  @Override
  public CreditCard findCardByEmail(String emailOfOwner) {
    return creditCardInfoRepository
        .findByEmailOfOwner(emailOfOwner)
        .orElseThrow(CreditCardNotFoundException::new);
  }

  @Override
  public CreditCard findCardByCard(String card) {
    return creditCardInfoRepository
        .findByCard(card)
        .orElseThrow(CreditCardNotFoundException::new);
  }

  @Override
  public void existCard(String email) {
    Optional<CreditCard> card = creditCardInfoRepository.findByEmailOfOwner(email);
      if (card.isPresent()) {
          throw new BadRequestException("User has already had credit card!");
      }
  }

  @Override
  public CreditCardInfo addCreditCard(String email) {
    Map<String, String> accountResponse = accountInterface.getUserDetails(email).getBody();
    existCard(email);
    CreditCard creditCard = new CreditCard();
    assert accountResponse != null;
    creditCard.setOwner(accountResponse.get("fullName"));
    creditCard.setEmailOfOwner(accountResponse.get("email"));
    save(creditCard);
    eventInterface.createCard(email);
    return creditCard.toCreditCardInfo();
  }

  @Override
  public CreditCard getInfoAboutCard(String email) {
    return findCardByEmail(email);
  }

  //
  @Override
  public CreditCardInfo addMoney(MoneyToCardDto moneyToCardDto) {
    CreditCard creditCard = findCardByCard(moneyToCardDto.getCard());
    checkTurnover(creditCard.getEmailOfOwner());
    addMoneyTransaction(moneyToCardDto.getSuma(), creditCard);
    return creditCard.toCreditCardInfo();
  }

  @Override
  @Transactional
  public void addMoneyTransaction(float amount, CreditCard creditCard) {
    cardIsLock(creditCard);
    creditCard.setBalance(creditCard.getBalance() + amount);
    creditCard.setTurnover(creditCard.getTurnover() + amount);
    save(creditCard);
    Map<String, String> card=new HashMap<>();
    card.put("balance",String.valueOf(creditCard.getBalance()));
    card.put("email",creditCard.getEmailOfOwner());
    eventInterface.addMoney(card, amount);
  }

  @Override
  public CreditCardInfo transferMoney(MoneyToCardDto moneyToCardDto, String email) {
    CreditCard cardOfSubject = findCardByEmail(email);
    CreditCard cardOfObject = findCardByCard(moneyToCardDto.getCard());
    checkTurnover(cardOfSubject.getEmailOfOwner());
    transferMoneyTransaction(moneyToCardDto.getSuma(), cardOfSubject, cardOfObject);
    Map<String,String> cards=new HashMap<>();
    cards.put("subjectBalance", String.valueOf(cardOfSubject.getBalance()));
    cards.put("objectBalance", String.valueOf(cardOfObject.getBalance()));
    cards.put("subjectEmail", cardOfSubject.getEmailOfOwner());
    cards.put("objectEmail", cardOfObject.getEmailOfOwner());
    eventInterface.transaction(cards,moneyToCardDto.getSuma());
    return cardOfSubject.toCreditCardInfo();

  }

  @Override
  @Transactional
  public void transferMoneyTransaction(float amount, CreditCard cardOfSubject,
      CreditCard cardOfObject) {
    cardIsLock(cardOfObject);
    cardIsLock(cardOfSubject);
      if (cardOfSubject.getBalance() < amount) {
          throw new BadRequestException("Balance is smaller that suma of transaction!");
      }
    cardOfSubject.setBalance(cardOfSubject.getBalance() - amount);
    cardOfObject.setBalance(cardOfObject.getBalance() + amount);
    cardOfObject.setTurnover(cardOfObject.getTurnover() + amount);
    save(cardOfObject);
    save(cardOfSubject);
  }

  @Override
  public void checkTurnover(String email) {
    CreditCard creditCard = findCardByEmail(email);
    if (creditCard.getTurnover() > creditCard.getLimitOfTurnover()) {
      accountInterface.lockAccount(email);
      lockCard(email);
      throw new ResponseStatusException(HttpStatus.LOCKED,
          String.format("""
                  %s your account and card was locked.
                  Turnover on your card is %.2f that bigger than %.2f ua.
                  Present a document about earnings!""",
              creditCard.getOwner(), creditCard.getTurnover(), creditCard.getLimitOfTurnover()));
    }
  }

  @Override
  public String newTurnoverLimit(ChangeTurnoverLimitDto changeTurnoverLimitDto) {
    CreditCard creditCard = findCardByEmail(changeTurnoverLimitDto.getEmail());
    creditCard.setLimitOfTurnover(changeTurnoverLimitDto.getLimitOfTurnover());
    save(creditCard);

    eventInterface.newLimitOfTurnover(changeTurnoverLimitDto.getEmail());

    return "New turnover: "+ changeTurnoverLimitDto.getLimitOfTurnover();
  }

  @Override
  public void cardIsLock(CreditCard creditCard) {
      if (creditCard.isCardIsLocked()) {
          throw new CardIsLockException();
      }
  }

  @Override
  public String lockCard(String email) {
    CreditCard creditCard = findCardByEmail(email);
      if (creditCard.isCardIsLocked()) {
          throw new BadRequestException("Card has already locked");
      }
    creditCard.setCardIsLocked(true);
    save(creditCard);
    eventInterface.lockCard(email);
    return "Card was locked: " + creditCard.getCard();
  }

  @Override
  public String unlockCard(String email) {
    CreditCard creditCard = findCardByEmail(email);
      if (!creditCard.isCardIsLocked()) {
          throw new BadRequestException("Card has already unlocked");
      }
    creditCard.setCardIsLocked(false);
    save(creditCard);
    eventInterface.unlockCard(email);
    return "Card was unlocked: "+ creditCard.getCard();
  }
}
