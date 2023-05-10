package com.example.pawcare.entities;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.pawcare.entities.Accessory.BAD_WORDS;

public final class Badwords implements ConstraintValidator<Badword, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        for (String badWord : BAD_WORDS) {
            if (value.toLowerCase().contains(badWord)) {
                return false;
            }
        }

        return !value.trim().isEmpty();
    }
}
