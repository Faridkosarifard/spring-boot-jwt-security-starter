package io.github.faridkosarifard.jwtstarter.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value.length() < 8 || value.length() > 72) {
            return false;
        }
        boolean letter = value.chars().anyMatch(Character::isLetter);
        boolean digit = value.chars().anyMatch(Character::isDigit);
        return letter && digit;
    }
}
