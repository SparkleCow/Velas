package com.sparklecow.velas.exceptions;

public class ActivationTokenException extends Exception{
    public ActivationTokenException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " - " + "The verification token has expired. Please request a new token.";
    }
}

