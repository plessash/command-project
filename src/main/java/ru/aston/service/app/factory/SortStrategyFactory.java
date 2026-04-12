package ru.aston.service.app.factory;

import ru.aston.model.Car;
import ru.aston.service.strategy.SortStrategy;
import ru.aston.service.strategy.impl.BubbleSortStrategy;
import ru.aston.service.strategy.impl.EvenFieldSortDecorator;
import ru.aston.service.strategy.impl.InsertionSortStrategy;
import ru.aston.service.strategy.impl.SelectionSortStrategy;

public class SortStrategyFactory {

    public SortStrategy<Car> create(String choice) {
        SortStrategy<Car> bubble = new BubbleSortStrategy<>();
        SortStrategy<Car> insertion = new InsertionSortStrategy<>();
        SortStrategy<Car> selection = new SelectionSortStrategy<>();

        return switch (choice) {
            case "1" -> bubble;
            case "2" -> new EvenFieldSortDecorator<>(bubble, Car::getPower);
            case "3" -> new EvenFieldSortDecorator<>(bubble, Car::getYear);
            case "4" -> insertion;
            case "5" -> new EvenFieldSortDecorator<>(insertion, Car::getPower);
            case "6" -> new EvenFieldSortDecorator<>(insertion, Car::getYear);
            case "7" -> selection;
            case "8" -> new EvenFieldSortDecorator<>(selection, Car::getPower);
            case "9" -> new EvenFieldSortDecorator<>(selection, Car::getYear);
            default -> throw new IllegalArgumentException("Некорректный алгоритм сортировки.");
        };
    }
}