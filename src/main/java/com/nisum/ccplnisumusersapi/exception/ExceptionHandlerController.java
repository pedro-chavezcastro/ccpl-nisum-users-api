package com.nisum.ccplnisumusersapi.exception;

import com.nisum.ccplnisumusersapi.crosscutting.constant.MessageErrorEnum;
import com.nisum.ccplnisumusersapi.crosscutting.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception e, HandlerMethod handlerMethod) {
        return generateErrorResponse(new TechnicalException(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler({NisumException.class})
    public ResponseEntity<Object> handleCCPLException(NisumException e) {
        return generateErrorResponse(e, HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException e, HandlerMethod handlerMethod) {

        NisumException exception = new BusinessException(MessageErrorEnum.NISUM003.getDescription(),
                MessageErrorEnum.NISUM003.getCode());

        List<String> collect = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return generateErrorResponse(exception, HttpStatus.BAD_REQUEST, collect);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        NisumException exception = new BusinessException(MessageErrorEnum.NISUM003.getDescription(),
                MessageErrorEnum.NISUM003.getCode());

        List<String> metadata = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField().concat(": ").concat(Objects.requireNonNull(
                        fieldError.getDefaultMessage())))
                .collect(Collectors.toList());

        return generateErrorResponse(exception, HttpStatus.BAD_REQUEST, metadata);
    }

    private ResponseEntity<Object> generateErrorResponse(NisumException exception, HttpStatus httpStatus,
                                                         List<String> metadata) {
        return ResponseEntity
                .status(httpStatus)
                .body(Util.buildResponse(exception, metadata));
    }

}