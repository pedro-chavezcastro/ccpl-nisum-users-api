package com.nisum.ccplnisumusersapi.exception;

public class BusinessException extends NisumException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String codeError) {
        super(message, codeError);
    }

}