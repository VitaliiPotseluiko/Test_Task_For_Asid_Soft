package com.bookmanager.app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PublicationYearValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicationYear {
    String message() default "can't be greater than current year!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
