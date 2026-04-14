package ru.aston.model;

import org.junit.jupiter.api.Test;
import ru.aston.service.strategy.SortStrategy;
import ru.aston.service.strategy.impl.BubbleSortStrategy;
import ru.aston.service.strategy.impl.EvenFieldSortDecorator;
import ru.aston.service.strategy.impl.InsertionSortStrategy;
import ru.aston.service.strategy.impl.SelectionSortStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvenFieldSortDecoratorTest {

    @Test
    void sort_shouldSortOnlyCarsWithEvenPower() {
        SortStrategy<Car> delegate = new BubbleSortStrategy<>();
        EvenFieldSortDecorator<Car> strategy =
                new EvenFieldSortDecorator<>(delegate, Car::getPower);

        List<Car> cars = new ArrayList<>(List.of(
                Car.builder().model("A").power(101).year(2020).build(),
                Car.builder().model("B").power(200).year(2018).build(),
                Car.builder().model("C").power(103).year(2019).build(),
                Car.builder().model("D").power(150).year(2022).build(),
                Car.builder().model("E").power(105).year(2021).build()
        ));

        strategy.sort(cars, Car.BY_POWER);

        assertEquals("A", cars.get(0).getModel());
        assertEquals("D", cars.get(1).getModel());
        assertEquals("C", cars.get(2).getModel());
        assertEquals("B", cars.get(3).getModel());
        assertEquals("E", cars.get(4).getModel());
    }

    @Test
    void sort_shouldSortOnlyCarsWithEvenYear() {
        SortStrategy<Car> delegate = new InsertionSortStrategy<>();
        EvenFieldSortDecorator<Car> strategy =
                new EvenFieldSortDecorator<>(delegate, Car::getYear);

        List<Car> cars = new ArrayList<>(List.of(
                Car.builder().model("A").power(180).year(2021).build(),
                Car.builder().model("B").power(220).year(2020).build(),
                Car.builder().model("C").power(160).year(2019).build(),
                Car.builder().model("D").power(140).year(2018).build()
        ));

        strategy.sort(cars, Car.BY_YEAR);

        assertEquals("A", cars.get(0).getModel());
        assertEquals("D", cars.get(1).getModel());
        assertEquals("C", cars.get(2).getModel());
        assertEquals("B", cars.get(3).getModel());
    }

    @Test
    void sort_shouldKeepListUnchanged_whenNoEvenFieldValues() {
        SortStrategy<Car> delegate = new SelectionSortStrategy<>();
        EvenFieldSortDecorator<Car> strategy =
                new EvenFieldSortDecorator<>(delegate, Car::getPower);

        List<Car> cars = new ArrayList<>(List.of(
                Car.builder().model("A").power(101).year(2021).build(),
                Car.builder().model("B").power(103).year(2019).build(),
                Car.builder().model("C").power(105).year(2017).build()
        ));

        strategy.sort(cars, Car.BY_POWER);

        assertEquals("A", cars.get(0).getModel());
        assertEquals("B", cars.get(1).getModel());
        assertEquals("C", cars.get(2).getModel());
    }

    @Test
    void sort_shouldSortAllElements_whenAllFieldValuesAreEven() {
        SortStrategy<Car> delegate = new BubbleSortStrategy<>();
        EvenFieldSortDecorator<Car> strategy =
                new EvenFieldSortDecorator<>(delegate, Car::getPower);

        List<Car> cars = new ArrayList<>(List.of(
                Car.builder().model("A").power(200).year(2020).build(),
                Car.builder().model("B").power(100).year(2018).build(),
                Car.builder().model("C").power(300).year(2022).build()
        ));

        strategy.sort(cars, Car.BY_POWER);

        assertEquals("B", cars.get(0).getModel());
        assertEquals("A", cars.get(1).getModel());
        assertEquals("C", cars.get(2).getModel());
    }

    @Test
    void getName_shouldReturnExpectedName() {
        SortStrategy<Car> delegate = new BubbleSortStrategy<>();
        EvenFieldSortDecorator<Car> strategy =
                new EvenFieldSortDecorator<>(delegate, Car::getPower);

        assertEquals(
                "Сортировка элементов с четным значением (Пузырьковая сортировка)",
                strategy.getName()
        );
    }
}