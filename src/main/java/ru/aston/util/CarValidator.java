package ru.aston.util;

public final class CarValidator {

    private CarValidator() {
    }

    public static void validateCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным!");
        }
    }

    public static void validateCar(String model, int power, int year) {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Модель не может быть пустой!");
        }
        if (power < 0) {
            throw new IllegalArgumentException("Мощность не может быть отрицательной!");
        }
        if (year < 0) {
            throw new IllegalArgumentException("Год не может быть отрицательным!");
        }
    }
}