package ru.aston.model;

import ru.aston.repository.CarDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomFillStrategy implements CarFillStrategy {

    private static final double DEVIATION_RANGE = 0.05;
    private static final int POSSIBLE_VALUES_RANGE = 2;
    private static final int TAKE_ZERO = 1;
    private static final int BASE_YEAR = 2010;
    private static final int OFFSET_YEAR = 15;

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
            int variation = (int) (basePower * DEVIATION_RANGE);
            int power = basePower + (random.nextInt(variation * POSSIBLE_VALUES_RANGE + TAKE_ZERO) - variation);
            int year = BASE_YEAR + random.nextInt(OFFSET_YEAR);

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
