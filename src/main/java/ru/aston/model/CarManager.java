package ru.aston.model;

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
