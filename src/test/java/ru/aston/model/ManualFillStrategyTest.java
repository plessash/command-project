package ru.aston.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.aston.service.strategy.impl.ManualFillStrategy;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class ManualFillStrategyTest {

    private final InputStream originalSystemIn = System.in;

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalSystemIn);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void shouldCreateCorrectNumberOfCarsWithManualInput() {
        provideInput("Lada\n106\n2024\nBMW\n250\n2023\n");
        int requestedCount = 2;
        List<Car> result = new ManualFillStrategy().fill(requestedCount);

        assertAll(
                () -> assertEquals(requestedCount, result.size()),
                () -> assertEquals("Lada", result.getFirst().getModel()),
                () -> assertEquals(106, result.getFirst().getPower()),
                () -> assertEquals(2024, result.getFirst().getYear()),
                () -> assertEquals("BMW", result.get(1).getModel()),
                () -> assertEquals(250, result.get(1).getPower()),
                () -> assertEquals(2023, result.get(1).getYear())
        );
    }

    @Test
    void shouldReturnEmptyListIfCountIsZero() {
        List<Car> result = new ManualFillStrategy().fill(0);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldCreateCorrectCarsWithSpacesInModel() {
        provideInput("Lada Granta Drive Active\n106\n2024\n");
        int requestedCount = 1;
        List<Car> result = new ManualFillStrategy().fill(requestedCount);

        assertAll(
                () -> assertEquals(requestedCount, result.size()),
                () -> assertEquals("Lada Granta Drive Active", result.getFirst().getModel())
        );
    }

    @Test
    void shouldRetryInputWhenInvalidNumberEntered() {
        provideInput("Volga\nмного\n150\nстарый\n2010\n");
        int requestedCount = 1;
        List<Car> result = new ManualFillStrategy().fill(requestedCount);

        assertAll(
                () -> assertEquals(requestedCount, result.size()),
                () -> assertEquals(150, result.getFirst().getPower()),
                () -> assertEquals(2010, result.getFirst().getYear())
        );
    }

}
