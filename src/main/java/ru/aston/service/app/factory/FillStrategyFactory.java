package ru.aston.service.app.factory;

import ru.aston.service.app.console.ConsoleInputService;
import ru.aston.service.strategy.CarFillStrategy;
import ru.aston.service.strategy.impl.FileFillStrategy;
import ru.aston.service.strategy.impl.ManualFillStrategy;
import ru.aston.service.strategy.impl.RandomFillStrategy;

public class FillStrategyFactory {

    private final ConsoleInputService inputService;

    public FillStrategyFactory(ConsoleInputService inputService) {
        this.inputService = inputService;
    }

    public CarFillStrategy create(String choice) {
        return switch (choice) {
            case "1" -> {
                String path = inputService.readLine("Введите путь к JSON-файлу: ");
                yield new FileFillStrategy(path);
            }
            case "2" -> new RandomFillStrategy();
            case "3" -> new ManualFillStrategy();
            default -> throw new IllegalArgumentException("Некорректный способ заполнения.");
        };
    }
}
