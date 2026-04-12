package ru.aston.service.app.console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleInputServiceTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void readChoice_shouldReturnTrimmedInput() {
        ConsoleInputService service = createService("  2  \n");

        String result = service.readChoice();

        assertEquals("2", result);
    }

    @Test
    void readLine_shouldPrintMessageAndReturnTrimmedInput() {
        ConsoleInputService service = createService("  hello world  \n");

        String result = service.readLine("Введите строку: ");

        assertEquals("hello world", result);
        assertTrue(outputStream.toString().contains("Введите строку: "));
    }

    @Test
    void readPositiveInt_shouldReturnPositiveNumberFromFirstAttempt() {
        ConsoleInputService service = createService("15\n");

        int result = service.readPositiveInt("Введите число: ");

        assertEquals(15, result);
        assertTrue(outputStream.toString().contains("Введите число: "));
    }

    @Test
    void readPositiveInt_shouldRetryWhenInputIsZero() {
        ConsoleInputService service = createService("0\n7\n");

        int result = service.readPositiveInt("Введите число: ");

        assertEquals(7, result);
        String output = outputStream.toString();
        assertTrue(output.contains("Число должно быть больше 0."));
    }

    @Test
    void readPositiveInt_shouldRetryWhenInputIsNegative() {
        ConsoleInputService service = createService("-5\n9\n");

        int result = service.readPositiveInt("Введите число: ");

        assertEquals(9, result);
        String output = outputStream.toString();
        assertTrue(output.contains("Число должно быть больше 0."));
    }

    @Test
    void readPositiveInt_shouldRetryWhenInputIsNotNumber() {
        ConsoleInputService service = createService("abc\n11\n");

        int result = service.readPositiveInt("Введите число: ");

        assertEquals(11, result);
        String output = outputStream.toString();
        assertTrue(output.contains("Введите корректное целое число."));
    }

    @Test
    void readPositiveInt_shouldRetrySeveralTimesUntilValidNumber() {
        ConsoleInputService service = createService("abc\n-1\n0\n20\n");

        int result = service.readPositiveInt("Введите число: ");

        assertEquals(20, result);
        String output = outputStream.toString();
        assertTrue(output.contains("Введите корректное целое число."));
        assertTrue(output.contains("Число должно быть больше 0."));
    }

    private ConsoleInputService createService(String input) {
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        return new ConsoleInputService(new Scanner(inputStream));
    }
}