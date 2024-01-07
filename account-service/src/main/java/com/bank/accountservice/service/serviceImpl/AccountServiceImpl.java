package com.bank.accountservice.service.serviceImpl;

import com.bank.accountservice.feign.EventInterface;
import com.bank.accountservice.feign.MailInterface;
import com.bank.accountservice.model.entities.Account;
import com.bank.accountservice.model.exceptions.AccountNotFoundException;
import com.bank.accountservice.model.exceptions.BadRequestException;
import com.bank.accountservice.repositories.AccountInfoRepository;
import com.bank.accountservice.service.AccountService;
import com.bank.accountservice.web.controllers.account.AccountDto;
import com.bank.accountservice.web.controllers.account.PasswordDto;
import com.bank.accountservice.web.controllers.code.Code;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final MailInterface mailInterface;
    private final PasswordEncoder passwordEncoder;
    private final AccountInfoRepository accountInfoRepository;
    private final EventInterface eventInterface;

    @Override
    public Account findByEmail(String email) {
        return accountInfoRepository
            .findByEmailIgnoreCase(email)
            .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public void save(Account account) {
        if (account != null) {
            accountInfoRepository.save(account);
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public void isExist(String email) {
        Optional<Account> someAccount = accountInfoRepository.findByEmailIgnoreCase(email);
        if (someAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email has already used!");
        }
    }

    @Override
    public boolean isAccountTableEmpty() {
        List<Account> accounts = accountInfoRepository.findAll();
        return accounts.isEmpty();
    }


    @Override
    public String addAccount(AccountDto accountDto) {
        isExist(accountDto.getEmail());
        Account account = Account.builder()
            .fullName(accountDto.getFullName())
            .email(accountDto.getEmail())
            .password(passwordEncoder.encode(accountDto.getPassword()))
            .lockStatus(false)
            .failedAttempts(0)
            .roles(List.of("ROLE_CUSTOMER"))
            .build();
        if (isAccountTableEmpty()) {
            account.setRoles(List.of("ROLE_ADMINISTRATOR"));
        }
        save(account);
        eventInterface.createAccount(account.getEmail());
        return "Account was created";
    }


//    public void resetAccountAttempts(String email) {
//        Account account = accountData.findAccountByEmail(email);
//        account.setFailedAttempts(0);
//        accountData.saveAccount(account);
//    }
//
//    public void increaseFailedAttempts(Account account) {
//        eventService.authenticationFailed(account.getEmail());
//        account.setFailedAttempts(account.getFailedAttempts() + 1);
//        accountData.saveAccount(account);
//    }
    @Override
    public String lockAccount(String email) {
        Account account = findByEmail(email);
        if (account.getRoles().contains("ROLE_ADMINISTRATOR")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin can`t be locked!");
        }
        if (!account.isLockStatus()) {
            account.setLockStatus(true);
            account.setFailedAttempts(0);
            save(account);
            eventInterface.lockAccount(email);
            return "Lock";
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account hasn`t unlocked!");
    }

    @Override
    public String unlockAccount(String email) {
        Account account = findByEmail(email);
        if (account.isLockStatus()) {
            account.setLockStatus(false);
            account.setFailedAttempts(0);
            save(account);
            eventInterface.unlockAccount(email);
            return "Unlock";
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account hasn`t locked!");
    }

    @Override
    public void passwordsIsEquals(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            throw new BadRequestException("Password doesn't match");
        }
    }

    @Override
    public void checkCodeForChangePassword(String userCode, HttpSession session) {
        String code = (String) session.getAttribute("codeChangePassword");
        codesIsEquals(code, userCode);
    }

    @Override
    public void codesIsEquals(String code, String inputCode) {
        if (!code.equals(inputCode)) {
            throw new BadRequestException("Code is wrong");
        }
    }

    @Override
    public void sendCodeToEmailForChangePassword(String email, HttpSession session) {
        Code code = new Code();
        mailInterface.sendCodeForChangePassword(email, code.getCode());
        session.setAttribute("userEmailChangePassword", email);
        session.setAttribute("codeChangePassword", code.getCode());
    }

    @Override
    public Map<String, String> getUserDetails(String email) {
        Map<String, String> userDetails = new TreeMap<>();
        Account account = findByEmail(email);
        userDetails.put("email", account.getEmail());
        userDetails.put("fullName", account.getFullName());
        userDetails.put("password", account.getPassword());
        userDetails.put("isLock", String.valueOf(account.isLockStatus()));
        userDetails.put("role", account.getRoles().get(0));
        return userDetails;
    }


    @Override
    public void changePassword(PasswordDto passwordDto, HttpSession session) {
        passwordsIsEquals(passwordDto.getPassword(), passwordDto.getRepeatPassword());
        String email = (String) session.getAttribute("userEmailChangePassword");
        Account account = findByEmail(email);
        account.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        save(account);
        eventInterface.changePassword(email);
    }

}
