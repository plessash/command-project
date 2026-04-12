package ru.aston.service.app.factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.aston.model.Car;
import ru.aston.service.strategy.SortStrategy;
import ru.aston.service.strategy.impl.BubbleSortStrategy;
import ru.aston.service.strategy.impl.InsertionSortStrategy;
import ru.aston.service.strategy.impl.EvenFieldSortDecorator;
import ru.aston.service.strategy.impl.SelectionSortStrategy;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SortStrategyFactoryTest {

    private final SortStrategyFactory factory = new SortStrategyFactory();

    static Stream<Arguments> strategyChoices() {
        return Stream.of(
                Arguments.of("1", BubbleSortStrategy.class),
                Arguments.of("2", EvenFieldSortDecorator.class),
                Arguments.of("3", EvenFieldSortDecorator.class),
                Arguments.of("4", InsertionSortStrategy.class),
                Arguments.of("5", EvenFieldSortDecorator.class),
                Arguments.of("6", EvenFieldSortDecorator.class),
                Arguments.of("7", SelectionSortStrategy.class),
                Arguments.of("8", EvenFieldSortDecorator.class),
                Arguments.of("9", EvenFieldSortDecorator.class)
        );
    }

    @ParameterizedTest
    @MethodSource("strategyChoices")
    void create_shouldReturnExpectedStrategy(String choice, Class<?> expectedClass) {
        SortStrategy<Car> result = factory.create(choice);

        assertInstanceOf(expectedClass, result);
    }

    @Test
    void create_shouldThrowException_whenChoiceIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> factory.create("999"));
    }
}