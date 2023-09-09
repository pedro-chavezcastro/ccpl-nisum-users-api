package com.nisum.ccplnisumusersapi.crosscutting.util;

import com.nisum.ccplnisumusersapi.exception.NisumException;
import com.nisum.ccplnisumusersapi.model.ErrorDto;

import java.time.LocalDateTime;
import java.util.List;

public class Util {

    private Util() {
        super();
    }

    public static ErrorDto buildResponse(NisumException exception, List<String> metadata) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(exception.getCodeError());
        errorDto.setMessage(exception.getMessage());
        errorDto.setTimeStamp(String.valueOf(LocalDateTime.now()));
        errorDto.setMetadata(metadata);
        return errorDto;
    }

}
