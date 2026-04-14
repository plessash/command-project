package ru.aston.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarValidatorTest {

    @Test
    void validateCount_shouldNotThrow_whenCountIsZeroOrPositive() {
        assertDoesNotThrow(() -> CarValidator.validateCount(0));
        assertDoesNotThrow(() -> CarValidator.validateCount(5));
    }

    @Test
    void validateCount_shouldThrow_whenCountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> CarValidator.validateCount(-1));
    }

    @Test
    void validateCar_shouldNotThrow_whenAllValuesAreValid() {
        assertDoesNotThrow(() -> CarValidator.validateCar("BMW", 250, 2020));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void validateCar_shouldThrow_whenModelIsInvalid(String model) {
        assertThrows(IllegalArgumentException.class, () -> CarValidator.validateCar(model, 250, 2020));
    }

    @Test
    void validateCar_shouldThrow_whenPowerIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> CarValidator.validateCar("BMW", -1, 2020));
    }

    @Test
    void validateCar_shouldThrow_whenYearIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> CarValidator.validateCar("BMW", 250, -1));
    }
}