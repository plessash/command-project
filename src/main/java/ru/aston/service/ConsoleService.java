package ru.aston.service;

import ru.aston.model.Car;
import ru.aston.service.strategy.impl.*;
import ru.aston.service.strategy.SortStrategy;

import java.util.List;
import java.util.Scanner;

public class ConsoleService {
    private final Scanner scanner = new Scanner(System.in);
    private final CarManager carManager = new CarManager();

    public void start() {
        boolean running = true;

        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> handleFillWorkflow();
                case "2" -> {
                    running = false;
                    System.out.println("Программа завершена.");
                }
                default -> System.out.println("Некорректный пункт меню.");
            }

            System.out.println();
        }
    }

    private void printMainMenu() {
        System.out.println("===== ГЛАВНОЕ МЕНЮ =====");
        System.out.println("1. Создать коллекцию автомобилей");
        System.out.println("2. Выход");
        System.out.print("Выберите пункт: ");
    }

    private void handleFillWorkflow() {
        int count = readPositiveInt("Введите количество автомобилей: ");

        chooseFillStrategy();

        try {
            List<Car> cars = carManager.fillData(count);

            if (cars.isEmpty()) {
                System.out.println("Список автомобилей пуст.");
                return;
            }

            System.out.println();
            System.out.println("Сформированная коллекция:");
            printCars(cars);
            sortCars(cars);

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void chooseFillStrategy() {
        System.out.println();
        System.out.println("Выберите способ заполнения:");
        System.out.println("1. Из файла");
        System.out.println("2. Случайно");
        System.out.println("3. Вручную");
        System.out.print("Ваш выбор: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                System.out.print("Введите путь к JSON-файлу: ");
                String path = scanner.nextLine().trim();
                carManager.setStrategy(new FileFillStrategy(path));
            }
            case "2" -> carManager.setStrategy(new RandomFillStrategy());
            case "3" -> carManager.setStrategy(new ManualFillStrategy());
            default -> throw new IllegalArgumentException("Некорректный способ заполнения.");
        }
    }

    private void printCars(List<Car> cars) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            System.out.println((i + 1) + ". "
                    + car.getModel()
                    + " | " + car.getPower() + " л.с."
                    + " | " + car.getYear() + " год");
        }
    }

    private int readPositiveInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(scanner.nextLine().trim());

                if (value <= 0) {
                    System.out.println("Число должно быть больше 0.");
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное целое число.");
            }
        }
    }
    private SortStrategy<Car> chooseSortStrategy(String choice) {
        return switch (choice) {
            case "1" -> new BubbleSortStrategy<>();
            case "2" -> new InsertionSortStrategy<>();
            case "3" -> new SelectionSortStrategy<>();
            default -> throw new IllegalArgumentException("Некорректный алгоритм сортировки.");
        };
    }
    public void sortCars(List<Car> cars) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите алгоритм сортировки:");
        System.out.println("1 - Пузырьковая сортировка");
        System.out.println("2 - Сортировка вставками");
        System.out.println("3 - Сортировка выбором");

        String choice = scanner.nextLine();


        SortStrategy<Car> sortStrategy = chooseSortStrategy(choice);
        sortStrategy.sort(cars, Car.BY_POWER);
        printCars(cars);
    }
}
