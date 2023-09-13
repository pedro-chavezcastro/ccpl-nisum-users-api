package com.nisum.ccplnisumusersapi.crosscutting.util;

import com.nisum.ccplnisumusersapi.exception.BusinessException;
import com.nisum.ccplnisumusersapi.exception.NisumException;
import com.nisum.ccplnisumusersapi.model.ErrorDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class UtilTest {

    @Test
    void buildResponseTest() {
        // Given
        String errorMessage = "Error";
        String errorCode = "001";
        NisumException exception = new BusinessException(errorMessage, errorCode);
        List<String> metadata = Collections.emptyList();
        // When
        ErrorDto result = Util.buildResponse(exception, metadata);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(errorMessage, result.getMessage());
        Assertions.assertEquals(errorCode, result.getCode());
    }
}