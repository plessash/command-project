package ru.aston.service.strategy.impl;

import ru.aston.model.Car;
import ru.aston.repository.CarDataRepository;
import ru.aston.service.strategy.CarFillStrategy;
import ru.aston.util.CarValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomFillStrategy implements CarFillStrategy {

    private final Random random = new Random();
    private final List<Car> data = CarDataRepository.getCars();

    @Override
    public List<Car> fill(int count) {
        CarValidator.validateCount(count);

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Car randomCar = data.get(random.nextInt(data.size()));

            Car newCar = new Car(randomCar.getModel(), randomCar.getPower(), randomCar.getYear());
            cars.add(newCar);
        }
        return cars;
    }
}
