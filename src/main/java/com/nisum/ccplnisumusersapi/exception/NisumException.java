package com.nisum.ccplnisumusersapi.exception;

import com.nisum.ccplnisumusersapi.crosscutting.constant.MessageErrorEnum;
import lombok.Getter;

@Getter
public class NisumException extends RuntimeException {

    private String codeError = MessageErrorEnum.NISUM000.getCode();

    public NisumException(String message) {
        super(message);
    }

    public NisumException(String message, String codeError) {
        super(message);
        this.codeError = codeError;
    }

}