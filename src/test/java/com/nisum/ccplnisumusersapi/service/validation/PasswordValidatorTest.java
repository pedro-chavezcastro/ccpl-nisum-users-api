package com.nisum.ccplnisumusersapi.service.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    private PasswordValidator passwordValidator;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(passwordValidator, "passwordRegex", "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    @Test
    void isValidTest() {
        // Given
        String value = "Febrero08*34";
        // When
        boolean result = passwordValidator.isValid(value, constraintValidatorContext);
        // Then
        assertTrue(result);
    }
}