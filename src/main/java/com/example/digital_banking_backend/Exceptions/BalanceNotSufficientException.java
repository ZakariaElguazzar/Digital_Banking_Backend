package com.example.digital_banking_backend.Exceptions;

public class BalanceNotSufficientException extends Exception{
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
