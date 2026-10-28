package com.instantpay.app.user.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.instantpay.app.shared.infra.RestErrorMessage;
import com.instantpay.app.user.exceptions.ClosedAccountException;
import com.instantpay.app.user.exceptions.UserNotFoundException;

@ControllerAdvice
public class UserExceptionHandler {
    
    private ResponseEntity<RestErrorMessage> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new RestErrorMessage(status, message));
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException exception) {
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ClosedAccountException.class)
    private ResponseEntity<RestErrorMessage> closedAccountHandler(ClosedAccountException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
