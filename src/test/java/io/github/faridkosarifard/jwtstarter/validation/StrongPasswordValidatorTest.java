package io.github.faridkosarifard.jwtstarter.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StrongPasswordValidatorTest {

    private final StrongPasswordValidator validator = new StrongPasswordValidator();

    @Test
    void acceptsPasswordsWithLettersAndDigits() {
        assertThat(validator.isValid("ValidPass1", null)).isTrue();
        assertThat(validator.isValid("short1A", null)).isFalse();
        assertThat(validator.isValid("NoDigitsHere", null)).isFalse();
        assertThat(validator.isValid("12345678", null)).isFalse();
        assertThat(validator.isValid(null, null)).isFalse();
        assertThat(validator.isValid("x".repeat(73) + "1", null)).isFalse();
    }
}
