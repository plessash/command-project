package ru.aston.service;

import ru.aston.model.Car;
import ru.aston.service.strategy.CarFillStrategy;
import ru.aston.service.strategy.SortStrategy;

import java.util.List;
import java.util.Comparator;

public class CarManager {
    private CarFillStrategy strategy;

    public void setStrategy(CarFillStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Car> fillData(int count) {
        if (strategy == null) throw new IllegalStateException("Стратегия не выбрана!");
        return strategy.fill(count);
    }

    public void sortCars(List<Car> cars, SortStrategy<Car> strategy, Comparator<Car> comparator) {
        if (strategy == null) throw new IllegalStateException("Стратегия сортировки не выбрана!");
        strategy.sort(cars, comparator);
    }
}
