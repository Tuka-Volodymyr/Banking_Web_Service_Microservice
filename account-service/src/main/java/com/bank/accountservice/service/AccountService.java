package com.bank.accountservice.service;

import com.bank.accountservice.model.entities.Account;
import com.bank.accountservice.web.controllers.account.AccountDto;
import com.bank.accountservice.web.controllers.account.PasswordDto;
import jakarta.servlet.http.HttpSession;
import java.util.Map;


public interface AccountService {

    String addAccount(AccountDto accountDto);

    boolean isAccountTableEmpty();

    void isExist(String email);

    void save(Account account);

    Account findByEmail(String email);

    String unlockAccount(String email);

    String lockAccount(String email);

    void checkCodeForChangePassword(String userCode, HttpSession session);

    void changePassword(PasswordDto passwordDto, HttpSession session);

    void passwordsIsEquals(String password, String repeatPassword);

    void codesIsEquals(String code, String inputCode);

    void sendCodeToEmailForChangePassword(String email, HttpSession session);

    Map<String, String> getUserDetails(String email);
}
