package com.nisum.ccplnisumusersapi.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomPasswordValidation {

    String message() default "La contraseña no cunple con el formato de la expresión regular.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
