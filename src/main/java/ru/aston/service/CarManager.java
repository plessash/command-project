package ru.aston.service;

import ru.aston.model.Car;
import ru.aston.service.strategy.CarFillStrategy;
import ru.aston.service.strategy.SortStrategy;

import java.util.List;
import java.util.Comparator;

public class CarManager {
    private CarFillStrategy strategy;
    private SortStrategy<Car> sortStrategy;

    public void setSortStrategy(SortStrategy<Car> sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public void setStrategy(CarFillStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Car> fillData(int count) {
        if (strategy == null) throw new IllegalStateException("Стратегия не выбрана!");
        return strategy.fill(count);
    }

    public void sortCars(List<Car> cars, Comparator<Car> comparator) {
        if (sortStrategy == null) throw new IllegalStateException("Стратегия сортировки не выбрана!");
        sortStrategy.sort(cars, comparator);
    }
}
