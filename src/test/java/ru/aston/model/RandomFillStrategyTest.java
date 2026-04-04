package ru.aston.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomFillStrategyTest {

    private final RandomFillStrategy strategy = new RandomFillStrategy();

    @Test
    void shouldReturnCorrectNumberOfCars() {
        int requestedCount = 10;
        List<Car> result = strategy.fill(requestedCount);

        assertEquals(requestedCount, result.size());
    }

    @Test
    void shouldReturnEmptyListIfCountIsZero() {
        List<Car> result = strategy.fill(0);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGenerateValidDataWithinRanges() {
        List<Car> result = strategy.fill(50);

        for (Car car : result) {
            assertAll(
                    () -> assertNotNull(car.getModel()),
                    () -> assertTrue(car.getYear() >= 2010 && car.getYear() <= 2024),
                    () -> assertTrue(car.getPower() > 0)
            );
        }
    }

    @Test
    void shouldGenerateDifferentCars() {
        List<Car> result = strategy.fill(100);

        long uniqueModels = result.stream()
                .map(Car::getModel)
                .distinct()
                .count();

        assertTrue(uniqueModels > 1);
    }

    @Test
    void shouldThrowExceptionWhenCountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> strategy.fill(-5));
    }

}
