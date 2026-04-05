package ru.aston.model;

import java.util.List;
import java.util.Scanner;

public class CarServiceDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CarManager service = new CarManager();

        System.out.print("Сколько машин нужно создать? ");
        int count = sc.nextInt();

        System.out.println("Вариант заполнения исходного массива данных:\n1 - Файл (JSON)\n2 - Рандом\n3 - Вручную");
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> service.setStrategy(new FileFillStrategy("cars.json"));
            case 2 -> service.setStrategy(new RandomFillStrategy());
            case 3 -> service.setStrategy(new ManualFillStrategy());
            default -> {
                System.out.println("Выберите вариант из предложенных: 1, 2 или 3.");
                return;
            }
        }

        List<Car> cars = service.fillData(count);
        cars.forEach(car -> System.out.println(car.getModel() + " (" + car.getYear() + ") - " + car.getPower() + " л.с."));
    }
}
