package ru.aston.service.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleServiceTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void start_shouldExitWhenUserChooses2() {
        runWithInput("""
                2
                """);

        String output = getOutput();

        assertTrue(output.contains("===== ГЛАВНОЕ МЕНЮ ====="));
        assertTrue(output.contains("Программа завершена."));
    }

    @Test
    void start_shouldPrintErrorForInvalidMainMenuChoice() {
        runWithInput("""
                999
                2
                """);

        String output = getOutput();

        assertTrue(output.contains("Некорректный пункт меню."));
        assertTrue(output.contains("Программа завершена."));
    }

    @Test
    void start_shouldHandleInvalidFillStrategyChoice() {
        runWithInput("""
                1
                2
                999
                2
                """);

        String output = getOutput();

        assertTrue(output.contains("Введите количество автомобилей: "));
        assertTrue(output.contains("Выберите способ заполнения:"));
        assertTrue(output.contains("Ошибка: Некорректный способ заполнения."));
        assertTrue(output.contains("Программа завершена."));
    }

    @Test
    void start_shouldCreateCarsWithRandomStrategyAndReturnToMainMenu() {
        runWithInput("""
                1
                2
                2
                3
                2
                """);

        String output = getOutput();

        assertTrue(output.contains("Сформированная коллекция:"));
        assertTrue(output.contains("===== ОПЕРАЦИИ С КОЛЛЕКЦИЕЙ ====="));
        assertTrue(output.contains("Возврат в главное меню."));
        assertTrue(output.contains("Программа завершена."));
    }

    @Test
    void start_shouldSortCarsAndNotSaveToFileWhenUserChoosesNo() {
        runWithInput("""
                1
                3
                2
                1
                1
                1
                2
                3
                2
                """);

        String output = getOutput();

        assertTrue(output.contains("Сформированная коллекция:"));
        assertTrue(output.contains("Выберите алгоритм сортировки:"));
        assertTrue(output.contains("Выберите поле для сортировки:"));
        assertTrue(output.contains("Отсортированная коллекция:"));
        assertTrue(output.contains("Сохранить отсортированную коллекцию в файл? (1 - Да / 2 - Нет): "));
    }

    @Test
    void start_shouldPrintErrorForInvalidComparatorChoice() {
        runWithInput("""
                1
                2
                2
                1
                1
                999
                2
                """);

        String output = getOutput();

        assertTrue(output.contains("Сформированная коллекция:"));
        assertTrue(output.contains("Выберите поле для сортировки:"));
        assertTrue(output.contains("Ошибка: Некорректный выбор компаратора."));
    }

    private void runWithInput(String data) {
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputStream);

        ConsoleService consoleService = new ConsoleService();
        consoleService.start();
    }

    private String getOutput() {
        return outputStream.toString(StandardCharsets.UTF_8);
    }
}