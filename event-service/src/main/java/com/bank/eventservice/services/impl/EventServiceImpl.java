package com.bank.eventservice.services.impl;


import com.bank.eventservice.model.entity.GlobalEvent;
import com.bank.eventservice.model.entity.OperationEvent;
import com.bank.eventservice.repositories.GlobalEventInfoRepository;
import com.bank.eventservice.repositories.OperationEventInfoRepository;
import com.bank.eventservice.services.EventService;
import jakarta.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final HttpServletRequest request;
    private final OperationEventInfoRepository operationEventInfoRepository;
    private final GlobalEventInfoRepository globalEventInfoRepository;
    private final NumberFormat numberFormat = NumberFormat.getInstance();
    @Override
    public void saveOperationEvent(OperationEvent operationEvent) {
        if (operationEvent != null) {
            operationEventInfoRepository.save(operationEvent);
        } else {
            throw new NullPointerException();
        }

    }

    @Override
    public void saveGlobalEvent(GlobalEvent globalEvent) {
        if (globalEvent != null) {
            globalEventInfoRepository.save(globalEvent);
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public List<OperationEvent> findOperationEventByEmail(String subject, String object) {
        return operationEventInfoRepository.findBySubjectOrObject(subject, object);
    }

    @Override
    public List<OperationEvent> findOperationEventByEmailObjectAndData(String object, Date data) {
        return operationEventInfoRepository.findByObjectAndDate(object, data);
    }

    @Override
    public List<GlobalEvent> getGlobalEvents() {
        return globalEventInfoRepository.findAllByOrderById();
    }

    @Override
    public List<OperationEvent> getOperationEvents(String email) {
        return findOperationEventByEmail(email, email);
    }

    @Override
    public GlobalEvent createAccount(String email) {
        GlobalEvent globalEvent = new GlobalEvent(
            new Date(),
            "CREATE_ACCOUNT",
            "Anonymous",
            email,
            request.getRequestURI()
        );
        saveGlobalEvent(globalEvent);
        return globalEvent;
    }

    @Override
    public void createCard(String email) {
        GlobalEvent globalEvent = new GlobalEvent(
            new Date(),
            "CREATE_CARD",
            email,
            email,
            request.getRequestURI()
        );
        saveGlobalEvent(globalEvent);
    }

    @Override
    public void changePassword(String email) {
        GlobalEvent globalEvent = new GlobalEvent(
            new Date(),
            "CHANGE_PASSWORD",
            email,
            email,
            request.getRequestURI()
        );
        saveGlobalEvent(globalEvent);
    }

    @SneakyThrows
    @Override
    public void addMoney(Map<String, String> card, float suma) {

        OperationEvent operationEvent = new OperationEvent(
            new Date(),
            "ADD_MONEY",
            suma,
            numberFormat.parse(card.get("balance")).floatValue(),
            0,
            card.get("email"),
            card.get("email"),
            request.getRequestURI()
        );
        saveOperationEvent(operationEvent);
    }

    @SneakyThrows
    @Override
    public OperationEvent transaction(Map<String, String> cards, float suma) {
        OperationEvent operationEvent = new OperationEvent(
            new Date(),
            "TRANSACTION",
            suma,
            numberFormat.parse(cards.get("subjectBalance")).floatValue(),
            numberFormat.parse(cards.get("objectBalance")).floatValue(),
            cards.get("subjectEmail"),
            cards.get("objectEmail"),
            request.getRequestURI()
        );
        saveOperationEvent(operationEvent);
        return operationEvent;
    }

    public void authenticationFailed(String email){
        GlobalEvent globalEvent =new GlobalEvent(
                 new Date(),
                "LOGIN_FAILED",
                email,
                email,
                request.getRequestURI()
        );
        saveGlobalEvent(globalEvent);
    }
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
    @Override
    public GlobalEvent lockAccount(String email) {
        GlobalEvent globalEvent = new GlobalEvent(
            new Date(),
            "LOCK_ACCOUNT",
            "Admin",
            email,
            request.getRequestURI()
        );
        saveGlobalEvent(globalEvent);
        return globalEvent;
    }

    @Override
    public GlobalEvent unlockAccount(String email) {
        GlobalEvent globalEvent = new GlobalEvent(
            new Date(),
            "UNLOCK_ACCOUNT",
            "Admin",
            email,
            request.getRequestURI()
        );
        saveGlobalEvent(globalEvent);
        return globalEvent;
    }

    @Override
    public GlobalEvent lockCard(String email) {
        GlobalEvent globalEvent = new GlobalEvent(
            new Date(),
            "LOCK_CARD",
            "Admin",
            email,
            request.getRequestURI()
        );
        saveGlobalEvent(globalEvent);
        return globalEvent;
    }

    @Override
    public GlobalEvent unlockCard(String email) {
        GlobalEvent globalEvent = new GlobalEvent(
            new Date(),
            "UNLOCK_CARD",
            "Admin",
            email,
            request.getRequestURI()
        );
        saveGlobalEvent(globalEvent);
        return globalEvent;
    }

    @Override
    public GlobalEvent newLimitOfTurnover(String email) {
        GlobalEvent globalEvent = new GlobalEvent(
            new Date(),
            "LIMIT_OF_TURNOVER_ENLARGED",
            "admin",
            email,
            request.getRequestURI()
        );
        saveGlobalEvent(globalEvent);
        return globalEvent;
    }

    @Override
    public void bruteForce(String email) {
        GlobalEvent globalEvent = new GlobalEvent(
            new Date(),
            "BRUTE_FORCE",
            email,
            request.getRequestURI(),
            request.getRequestURI()
        );
        saveGlobalEvent(globalEvent);
    }
//    private UserDetails getUserDetails() {
//        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }
}
