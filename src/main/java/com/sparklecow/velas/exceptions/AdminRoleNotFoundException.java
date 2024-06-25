package com.sparklecow.velas.exceptions;

public class AdminRoleNotFoundException extends Exception{

    public AdminRoleNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " - " + "Token has not an admin role.";
    }
}
