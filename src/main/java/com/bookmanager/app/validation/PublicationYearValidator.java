package com.bookmanager.app.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class PublicationYearValidator implements ConstraintValidator<PublicationYear, Integer> {
    @Override
    public boolean isValid(Integer publicationYear, ConstraintValidatorContext constraintValidatorContext) {
        return publicationYear <= LocalDate.now().getYear();
    }
}
