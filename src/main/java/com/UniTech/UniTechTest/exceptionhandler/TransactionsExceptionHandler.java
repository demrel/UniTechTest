package com.UniTech.UniTechTest.exceptionhandler;

import com.UniTech.UniTechTest.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TransactionsExceptionHandler {
    @ExceptionHandler({
            AccountIsNotExist.class,
            AccountNotFoundException.class,
            InactiveAccountException.class,
            InvalidAmountException.class,
            NotEnoughAmountException.class,
            SameAccountTransferException.class,
            TransferAmountException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleTransferException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
