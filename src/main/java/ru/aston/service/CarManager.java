package ru.aston.service;

import ru.aston.model.Car;
import ru.aston.service.strategy.CarFillStrategy;

import java.util.List;

public class CarManager {
    private CarFillStrategy strategy;

    public void setStrategy(CarFillStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Car> fillData(int count) {
        if (strategy == null) throw new IllegalStateException("Стратегия не выбрана!");
        return strategy.fill(count);
    }
}
