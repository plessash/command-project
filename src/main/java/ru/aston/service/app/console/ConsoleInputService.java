package ru.aston.service.app.console;

import java.util.Scanner;

public class ConsoleInputService {

    private final Scanner scanner;

    public ConsoleInputService(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readChoice() {
        return scanner.nextLine().trim();
    }

    public String readLine(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public int readPositiveInt(String message) {
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
}