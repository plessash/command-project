package ru.aston.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.service.strategy.impl.BubbleSortStrategy;
import ru.aston.service.strategy.impl.OddEvenSortDecorator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OddEvenSortDecoratorTest {

    private List<Car> cars;
    private OddEvenSortDecorator<Car> decoratorByPower;
    private OddEvenSortDecorator<Car> decoratorByYear;

    @BeforeEach
    void setUp() {
        cars = new ArrayList<>();
        cars.add(new Car("BMW", 625, 2021));
        cars.add(new Car("Audi", 472, 2020));
        cars.add(new Car("Lexus", 650, 2023));
        cars.add(new Car("Porsche", 598, 2022));
        cars.add(new Car("Mercedes", 501, 2024));

        decoratorByPower = new OddEvenSortDecorator<>(
                new BubbleSortStrategy<>(),
                Car::getPower,
                Comparator.comparingInt(Car::getPower)
        );

        decoratorByYear = new OddEvenSortDecorator<>(
                new BubbleSortStrategy<>(),
                Car::getYear,
                Comparator.comparingInt(Car::getYear)
        );
    }

    @Test
    void testOddEvenSortByPower() {
        Car bmw = cars.get(0);
        Car mercedes = cars.get(4);

        decoratorByPower.sort(cars, Comparator.comparingInt(Car::getPower));

        assertEquals(bmw, cars.get(0), "BMW (625, нечет) должен остаться на индексе 0");
        assertEquals(mercedes, cars.get(4), "Mercedes (501, нечет) должен остаться на индексе 4");

        List<Car> evenPowerCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.getPower() % 2 == 0) {
                evenPowerCars.add(car);
            }
        }

        for (int i = 0; i < evenPowerCars.size() - 1; i++) {
            assertTrue(evenPowerCars.get(i).getPower() <= evenPowerCars.get(i + 1).getPower(),
                    "Четные мощности должны быть отсортированы");
        }
    }

    @Test
    void testOddEvenSortByYear() {
        Car bmw = cars.get(0);
        Car lexus = cars.get(2);

        decoratorByYear.sort(cars, Comparator.comparingInt(Car::getYear));

        assertEquals(bmw, cars.get(0), "BMW (2021, нечет) должен остаться на индексе 0");
        assertEquals(lexus, cars.get(2), "Lexus (2023, нечет) должен остаться на индексе 2");

        List<Car> evenYearCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.getYear() % 2 == 0) {
                evenYearCars.add(car);
            }
        }

        for (int i = 0; i < evenYearCars.size() - 1; i++) {
            assertTrue(evenYearCars.get(i).getYear() <= evenYearCars.get(i + 1).getYear(),
                    "Четные года должны быть отсортированы");
        }
    }

    @Test
    void testEmptyList() {
        List<Car> emptyList = new ArrayList<>();
        decoratorByPower.sort(emptyList, Comparator.comparingInt(Car::getPower));
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void testAllOddValues() {
        List<Car> oddCars = new ArrayList<>();
        oddCars.add(new Car("Car1", 101, 2021));
        oddCars.add(new Car("Car2", 103, 2023));
        oddCars.add(new Car("Car3", 105, 2025));

        List<Car> original = new ArrayList<>(oddCars);
        decoratorByPower.sort(oddCars, Comparator.comparingInt(Car::getPower));

        assertEquals(original, oddCars, "Если все значения нечетные, порядок не меняется");
    }

    @Test
    void testAllEvenValues() {
        List<Car> evenCars = new ArrayList<>();
        evenCars.add(new Car("Car3", 300, 2022));
        evenCars.add(new Car("Car1", 100, 2020));
        evenCars.add(new Car("Car2", 200, 2024));

        decoratorByPower.sort(evenCars, Comparator.comparingInt(Car::getPower));

        for (int i = 0; i < evenCars.size() - 1; i++) {
            assertTrue(evenCars.get(i).getPower() <= evenCars.get(i + 1).getPower(),
                    "Если все значения четные, список полностью сортируется");
        }
    }
}
