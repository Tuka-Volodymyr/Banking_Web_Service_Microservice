package com.bank.cardservice.model.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Credit card do not exist")
public class CreditCardNotFoundException extends RuntimeException {

}
