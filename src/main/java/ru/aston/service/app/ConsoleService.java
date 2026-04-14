package ru.aston.service.app;

import ru.aston.model.Car;
import ru.aston.service.CarManager;
import ru.aston.service.app.console.ConsoleInputService;
import ru.aston.service.app.console.ConsolePrinter;
import ru.aston.service.app.factory.FillStrategyFactory;
import ru.aston.service.app.factory.SortStrategyFactory;
import ru.aston.service.strategy.CarFillStrategy;
import ru.aston.service.strategy.SortStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    private final ConsoleInputService inputService = new ConsoleInputService(scanner);
    private final ConsolePrinter printer = new ConsolePrinter();
    private final CarManager carManager = new CarManager();

    private final FillStrategyFactory fillStrategyFactory = new FillStrategyFactory(inputService);
    private final SortStrategyFactory sortStrategyFactory = new SortStrategyFactory();

    public void start() {
        boolean running = true;

        while (running) {
            printer.printMainMenu();
            String choice = inputService.readChoice();

            switch (choice) {
                case "1" -> handleFillWorkflow();
                case "2" -> {
                    running = false;
                    printer.printMessage("Программа завершена.");
                }
                default -> printer.printMessage("Некорректный пункт меню.");
            }

            System.out.println();
        }
    }

    private void handleFillWorkflow() {
        try {
            int count = inputService.readPositiveInt("Введите количество автомобилей: ");

            printer.printFillMenu();
            String fillChoice = inputService.readChoice();

            CarFillStrategy fillStrategy = fillStrategyFactory.create(fillChoice);
            carManager.setStrategy(fillStrategy);

            List<Car> cars = carManager.fillData(count);

            if (cars.isEmpty()) {
                printer.printMessage("Список автомобилей пуст.");
                return;
            }

            printer.printMessage("");
            printer.printMessage("Сформированная коллекция:");
            printer.printCars(cars);

            handleCollectionMenu(cars);

        } catch (IllegalArgumentException | IllegalStateException e) {
            printer.printMessage("Ошибка: " + e.getMessage());
        }
    }

    private void handleCollectionMenu(List<Car> cars) {
        boolean innerMenu = true;

        while (innerMenu) {
            printer.printActionsMenu();
            String choice = inputService.readChoice();

            switch (choice) {
                case "1" -> handleSortWorkflow(cars);
                case "2" -> saveCarsWorkflow(cars);
                case "3" -> {
                    innerMenu = false;
                    printer.printMessage("Возврат в главное меню.");
                }
                default -> printer.printMessage("Некорректный пункт меню.");
            }
        }
    }

    private void handleSortWorkflow(List<Car> cars) {
        printer.printSortMenu();
        String sortChoice = inputService.readChoice();

        SortStrategy<Car> sortStrategy = sortStrategyFactory.create(sortChoice);

        printer.printComparatorMenu();
        String comparatorChoice = inputService.readChoice();

        Comparator<Car> comparator = chooseComparator(comparatorChoice);

        carManager.setSortStrategy(sortStrategy);
        carManager.sortCars(cars, comparator);

        printer.printMessage("");
        printer.printMessage("Отсортированная коллекция:");
        printer.printCars(cars);

        String saveChoice = inputService.readLine("Сохранить отсортированную коллекцию в файл? (1 - Да / 2 - Нет): ");
        if ("1".equals(saveChoice)) {
            saveCarsWorkflow(cars);
        }
    }

    private void saveCarsWorkflow(List<Car> cars) {
        String path = inputService.readLine("Введите путь к файлу для сохранения: ");
        carManager.saveCarsToFile(path, cars);
        printer.printMessage("Коллекция успешно сохранена.");
    }

    private Comparator<Car> chooseComparator(String choice) {
        return switch (choice) {
            case "1" -> Car.BY_POWER;
            case "2" -> Car.BY_YEAR;
            default -> throw new IllegalArgumentException("Некорректный выбор компаратора.");
        };
    }
}