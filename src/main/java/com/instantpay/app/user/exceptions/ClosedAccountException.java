package com.instantpay.app.user.exceptions;

public class ClosedAccountException  extends RuntimeException {
    
    public ClosedAccountException() {
        super("Account is closed");
    }
    
    public ClosedAccountException(String message) {
        super(message);
    }
    
}
