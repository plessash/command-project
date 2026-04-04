package ru.aston.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomFillStrategy implements CarFillStrategy {
    private final Random random = new Random();
    private final Map<String, Integer> data = CarDataRepository.getModelsPower();
    private final List<String> models = new ArrayList<>(data.keySet());

    @Override
    public List<Car> fill(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным!");
        }

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String model = models.get(random.nextInt(models.size()));
            int basePower = data.get(model);
            int variation = (int) (basePower * 0.05);
            int power = basePower + (random.nextInt(variation * 2 + 1) - variation);
            int year = 2010 + random.nextInt(15);

            Car newCar = Car.builder()
                    .model(model)
                    .year(year)
                    .power(power)
                    .build();

            cars.add(newCar);
        }
        return cars;
    }
}
