package com.bank.eventservice.services;

import com.bank.eventservice.model.entity.GlobalEvent;
import com.bank.eventservice.model.entity.OperationEvent;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;

public interface EventService {

    void saveOperationEvent(OperationEvent operationEvent);

    void saveGlobalEvent(GlobalEvent globalEvent);

    List<OperationEvent> findOperationEventByEmail(String subject, String object);


    List<OperationEvent> findOperationEventByEmailObjectAndData(String object, Date data);

    List<GlobalEvent> getGlobalEvents();

    List<OperationEvent> getOperationEvents(String email);

    GlobalEvent createAccount(String email);

    void createCard(String email);

    void changePassword(String email);

    @SneakyThrows
    void addMoney(Map<String, String> card, float suma);

    @SneakyThrows
    OperationEvent transaction(Map<String, String> cards, float suma);


//    GlobalEvent lockAccount(AccountDto accountDto);
//
//    GlobalEvent unlockAccount(AccountDto accountDto);
//
//    GlobalEvent lockCard(CreditCardDto creditCardDto);
//
//    GlobalEvent unlockCard(CreditCardDto creditCardDto);
//
//    GlobalEvent newLimitOfTurnover(CreditCardDto creditCardDto);

    //    public void authorizationFailed(){
    //        UserDetails userDetails=getUserDetails();
    //        GlobalEvent globalEvent =new GlobalEvent(
    //                LocalDate.now().toString(),
    //                "ACCESS_DENIED",
    //                userDetails.getUsername(),
    //                userDetails.getUsername(),
    //                request.getRequestURI()
    //        );
    //        saveGlobalEvent(globalEvent);
    //    }
    GlobalEvent lockAccount(String email);

    GlobalEvent unlockAccount(String email);

    GlobalEvent lockCard(String email);

    GlobalEvent unlockCard(String email);

    GlobalEvent newLimitOfTurnover(String email);

    void bruteForce(String email);
}
