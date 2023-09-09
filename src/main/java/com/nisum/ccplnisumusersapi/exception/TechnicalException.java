package com.nisum.ccplnisumusersapi.exception;

public class TechnicalException extends NisumException {

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, String codeError) {
        super(message, codeError);
    }

}