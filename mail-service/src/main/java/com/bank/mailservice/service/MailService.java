package com.bank.mailservice.service;

public interface MailService {

    void sendCodeToEmailForChangePassword(String email, String code);
}
