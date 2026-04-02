package ru.aston.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManualFillStrategy implements CarFillStrategy {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public List<Car> fill(int count) {
        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            System.out.println("Автомобиль №" + (i + 1));
            System.out.print("Модель: ");
            String model = scanner.next();

            System.out.print("Мощность: ");
            int power = scanner.nextInt();

            System.out.print("Год: ");
            int year = scanner.nextInt();

            Car newCar = Car.builder()
                    .model(model)
                    .power(power)
                    .year(year)
                    .build();
            cars.add(newCar);
        }
        return cars;
    }
}
