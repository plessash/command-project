package ru.aston.service.app.console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.model.Car;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsolePrinterTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private ConsolePrinter consolePrinter;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        consolePrinter = new ConsolePrinter();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void printMainMenu_shouldPrintMainMenu() {
        consolePrinter.printMainMenu();

        String output = outputStream.toString();

        assertTrue(output.contains("===== ГЛАВНОЕ МЕНЮ ====="));
        assertTrue(output.contains("1. Создать коллекцию автомобилей"));
        assertTrue(output.contains("2. Выход"));
        assertTrue(output.contains("Выберите пункт: "));
    }

    @Test
    void printFillMenu_shouldPrintFillMenu() {
        consolePrinter.printFillMenu();

        String output = outputStream.toString();

        assertTrue(output.contains("Выберите способ заполнения:"));
        assertTrue(output.contains("1. Из файла"));
        assertTrue(output.contains("2. Случайно"));
        assertTrue(output.contains("3. Вручную"));
        assertTrue(output.contains("Ваш выбор: "));
    }

    @Test
    void printActionsMenu_shouldPrintActionsMenu() {
        consolePrinter.printActionsMenu();

        String output = outputStream.toString();

        assertTrue(output.contains("===== ОПЕРАЦИИ С КОЛЛЕКЦИЕЙ ====="));
        assertTrue(output.contains("1. Отсортировать коллекцию"));
        assertTrue(output.contains("2. Сохранить текущую коллекцию в файл"));
        assertTrue(output.contains("3. Вернуться в главное меню"));
        assertTrue(output.contains("Ваш выбор: "));
    }

    @Test
    void printSortMenu_shouldPrintSortMenu() {
        consolePrinter.printSortMenu();

        String output = outputStream.toString();

        assertTrue(output.contains("Выберите алгоритм сортировки:"));
        assertTrue(output.contains("1. Пузырьковая (обычная)"));
        assertTrue(output.contains("2. Пузырьковая (четная-нечетная по мощности)"));
        assertTrue(output.contains("3. Пузырьковая (четная-нечетная по году)"));
        assertTrue(output.contains("4. Сортировка вставками (обычная)"));
        assertTrue(output.contains("5. Сортировка вставками (четная-нечетная по мощности)"));
        assertTrue(output.contains("6. Сортировка вставками (четная-нечетная по году)"));
        assertTrue(output.contains("7. Сортировка выбором (обычная)"));
        assertTrue(output.contains("8. Сортировка выбором (четная-нечетная по мощности)"));
        assertTrue(output.contains("9. Сортировка выбором (четная-нечетная по году)"));
        assertTrue(output.contains("Ваш выбор: "));
    }

    @Test
    void printComparatorMenu_shouldPrintComparatorMenu() {
        consolePrinter.printComparatorMenu();

        String output = outputStream.toString();

        assertTrue(output.contains("Выберите поле для сортировки:"));
        assertTrue(output.contains("1. По мощности (л.с.)"));
        assertTrue(output.contains("2. По году выпуска"));
        assertTrue(output.contains("Ваш выбор: "));
    }

    @Test
    void printCars_shouldPrintAllCars() {
        List<Car> cars = List.of(
                Car.builder()
                        .model("BMW")
                        .power(250)
                        .year(2020)
                        .build(),
                Car.builder()
                        .model("Audi")
                        .power(190)
                        .year(2018)
                        .build()
        );

        consolePrinter.printCars(cars);

        String output = outputStream.toString();

        assertTrue(output.contains("1. BMW | 250 л.с. | 2020 год"));
        assertTrue(output.contains("2. Audi | 190 л.с. | 2018 год"));
    }

    @Test
    void printCars_shouldPrintNothingForEmptyList() {
        consolePrinter.printCars(List.of());

        String output = outputStream.toString();

        assertEquals("", output);
    }

    @Test
    void printMessage_shouldPrintMessageWithNewLine() {
        consolePrinter.printMessage("Тестовое сообщение");

        String output = outputStream.toString();

        assertTrue(output.contains("Тестовое сообщение"));
    }
}