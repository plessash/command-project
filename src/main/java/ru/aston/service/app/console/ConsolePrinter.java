package ru.aston.service.app.console;

import ru.aston.model.Car;

import java.util.List;

public class ConsolePrinter {

    public void printMainMenu() {
        System.out.println("===== ГЛАВНОЕ МЕНЮ =====");
        System.out.println("1. Создать коллекцию автомобилей");
        System.out.println("2. Выход");
        System.out.print("Выберите пункт: ");
    }

    public void printFillMenu() {
        System.out.println();
        System.out.println("Выберите способ заполнения:");
        System.out.println("1. Из файла");
        System.out.println("2. Случайно");
        System.out.println("3. Вручную");
        System.out.print("Ваш выбор: ");
    }

    public void printActionsMenu() {
        System.out.println();
        System.out.println("===== ОПЕРАЦИИ С КОЛЛЕКЦИЕЙ =====");
        System.out.println("1. Отсортировать коллекцию");
        System.out.println("2. Сохранить текущую коллекцию в файл");
        System.out.println("3. Вернуться в главное меню");
        System.out.print("Ваш выбор: ");
    }

    public void printSortMenu() {
        System.out.println();
        System.out.println("Выберите алгоритм сортировки:");
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
    }

    public void printComparatorMenu() {
        System.out.println();
        System.out.println("Выберите поле для сортировки:");
        System.out.println("1. По мощности (л.с.)");
        System.out.println("2. По году выпуска");
        System.out.print("Ваш выбор: ");
    }

    public void printCars(List<Car> cars) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            System.out.println((i + 1) + ". "
                    + car.getModel()
                    + " | " + car.getPower() + " л.с."
                    + " | " + car.getYear() + " год");
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}