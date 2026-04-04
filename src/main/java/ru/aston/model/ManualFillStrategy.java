package ru.aston.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManualFillStrategy implements CarFillStrategy {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public List<Car> fill(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным!");
        }

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            System.out.println("Автомобиль №" + (i + 1));

            System.out.print("Модель: ");
            String model = scanner.nextLine();

            int power = readIntSafely("Мощность (л.с.): ");
            int year = readIntSafely("Год выпуска: ");

            Car newCar = Car.builder()
                    .model(model)
                    .power(power)
                    .year(year)
                    .build();
            cars.add(newCar);

            System.out.println("-----------------------------");
        }
        return cars;
    }

    private int readIntSafely(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Ошибка! Введите целое число!");
            }
        }
    }
}
