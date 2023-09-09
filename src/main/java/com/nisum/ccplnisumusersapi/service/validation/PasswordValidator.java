package com.nisum.ccplnisumusersapi.service.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<CustomPasswordValidation, String> {

    @Value("${custom.password-regex}")
    private String passwordRegex;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(passwordRegex);
    }
}
