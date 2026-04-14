package ru.aston.service.app.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.aston.service.app.console.ConsoleInputService;
import ru.aston.service.strategy.CarFillStrategy;
import ru.aston.service.strategy.impl.FileFillStrategy;
import ru.aston.service.strategy.impl.ManualFillStrategy;
import ru.aston.service.strategy.impl.RandomFillStrategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FillStrategyFactoryTest {

    private ConsoleInputService inputService;
    private FillStrategyFactory fillStrategyFactory;

    @BeforeEach
    void setUp() {
        inputService = Mockito.mock(ConsoleInputService.class);
        fillStrategyFactory = new FillStrategyFactory(inputService);
    }

    @Test
    void create_shouldReturnFileFillStrategy_whenChoiceIs1() {
        when(inputService.readLine("Введите путь к JSON-файлу: ")).thenReturn("cars.json");

        CarFillStrategy result = fillStrategyFactory.create("1");

        assertInstanceOf(FileFillStrategy.class, result);
        verify(inputService).readLine("Введите путь к JSON-файлу: ");
    }

    @Test
    void create_shouldReturnRandomFillStrategy_whenChoiceIs2() {
        CarFillStrategy result = fillStrategyFactory.create("2");

        assertInstanceOf(RandomFillStrategy.class, result);
    }

    @Test
    void create_shouldReturnManualFillStrategy_whenChoiceIs3() {
        CarFillStrategy result = fillStrategyFactory.create("3");

        assertInstanceOf(ManualFillStrategy.class, result);
    }

    @Test
    void create_shouldThrowException_whenChoiceIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> fillStrategyFactory.create("999"));
    }
}