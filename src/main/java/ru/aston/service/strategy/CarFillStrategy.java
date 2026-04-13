package ru.aston.service.strategy;

import ru.aston.model.Car;

import java.util.List;

public interface CarFillStrategy {
    List<Car> fill(int count);
}
