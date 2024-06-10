package com.sparklecow.velas.exceptions;

public class NotFoundException extends Exception{

    private final Class<?> entityClass;
    public NotFoundException(String message, Class<?> entityClass) {
        super(message);
        this.entityClass = entityClass;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " - " + entityClass.getSimpleName() + " not found";
    }
}
