package ru.aston.service;

import ru.aston.model.Car;
import ru.aston.service.strategy.impl.FileFillStrategy;
import ru.aston.service.strategy.impl.ManualFillStrategy;
import ru.aston.service.strategy.impl.RandomFillStrategy;
import ru.aston.service.strategy.SortStrategy;
import ru.aston.service.strategy.impl.OddEvenSortDecorator;
import ru.aston.service.strategy.impl.BubbleSortStrategy;
import ru.aston.service.strategy.impl.InsertionSortStrategy;
import ru.aston.service.strategy.impl.SelectionSortStrategy;

import java.util.List;
import java.util.Scanner;
import java.util.Comparator;

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
            System.out.println();
            String sortChoice = askYesNo("Хотите отсортировать коллекцию? (1 - Да / 2 - Нет): ");
            if ("1".equals(sortChoice)) {
                handleSortWorkflow(cars);
            }

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
//    private SortStrategy<Car> chooseSortStrategy() {
//        System.out.println();
//        System.out.println("Выберите алгоритм сортировки:");
//        System.out.println("1. ");
//        System.out.println("2. ");
//        System.out.println("3. ");
//        System.out.print("Ваш выбор: ");
//
//        String choice = scanner.nextLine().trim();
//
//        return switch (choice) {
//            case "1" -> null;
//            case "2" -> null;
//            case "3" -> null;
//            default -> throw new IllegalArgumentException("Некорректный алгоритм сортировки.");
//        };
//    }

    private void handleSortWorkflow(List<Car> cars) {
        SortStrategy<Car> strategy = chooseSortStrategy();
        Comparator<Car> comparator = chooseComparator();

        carManager.sortCars(cars, strategy, comparator);

        System.out.println("\nОтсортированная коллекция:");
        printCars(cars);
    }

    private SortStrategy<Car> chooseSortStrategy() {
        System.out.println("\nВыберите алгоритм сортировки:");
        System.out.println("1. Пузырьковая (обычная)");
        System.out.println("2. Пузырьковая (четная-нечетная по мощности)");
        System.out.println("3. Пузырьковая (четная-нечетная по году)");
        System.out.println("4. Сортировка вставками (обычная)");
        System.out.println("5. Сортировка вставками (четная-нечетная по мощности)");
        System.out.println("6. Сортировка вставками (четная-нечетная по году)");
        System.out.println("7. Сортировка выбором (обычная)");
        System.out.println("8. Сортировка выбором (четная-нечетная по мощности)");
        System.out.println("9. Сортировка выбором (четная-нечетная по году)");
        System.out.print("Ваш выбор: ");

        String choice = scanner.nextLine().trim();

        SortStrategy<Car> bubble = new BubbleSortStrategy<>();
        SortStrategy<Car> insertion = new InsertionSortStrategy<>();
        SortStrategy<Car> selection = new SelectionSortStrategy<>();

        return switch (choice) {
            case "1" -> bubble;
            case "2" -> new OddEvenSortDecorator<>(bubble, Car::getPower, Car.BY_POWER);
            case "3" -> new OddEvenSortDecorator<>(bubble, Car::getYear, Car.BY_YEAR);
            case "4" -> insertion;
            case "5" -> new OddEvenSortDecorator<>(insertion, Car::getPower, Car.BY_POWER);
            case "6" -> new OddEvenSortDecorator<>(insertion, Car::getYear, Car.BY_YEAR);
            case "7" -> selection;
            case "8" -> new OddEvenSortDecorator<>(selection, Car::getPower, Car.BY_POWER);
            case "9" -> new OddEvenSortDecorator<>(selection, Car::getYear, Car.BY_YEAR);
            default -> throw new IllegalArgumentException("Некорректный алгоритм сортировки.");
        };
    }

    private Comparator<Car> chooseComparator() {
        System.out.println("\nВыберите поле для сортировки:");
        System.out.println("1. По мощности (л.с.)");
        System.out.println("2. По году выпуска");
        System.out.print("Ваш выбор: ");

        String choice = scanner.nextLine().trim();

        return switch (choice) {
            case "1" -> Car.BY_POWER;
            case "2" -> Car.BY_YEAR;
            default -> throw new IllegalArgumentException("Некорректный выбор компаратора.");
        };
    }

    private String askYesNo(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}
