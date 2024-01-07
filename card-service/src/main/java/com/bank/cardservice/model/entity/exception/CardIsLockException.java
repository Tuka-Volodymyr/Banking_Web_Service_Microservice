package com.bank.cardservice.model.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Card is lock!")
public class CardIsLockException extends RuntimeException {

}
