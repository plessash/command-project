package ru.aston.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileFillStrategy implements CarFillStrategy {
    private final Gson gson = new Gson();

    private final String fileName;

    public FileFillStrategy(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Car> fill(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным!");
        }

        Path path;
        try {
            if (fileName == null || fileName.isBlank()) {
                return new ArrayList<>();
            }
            path = Paths.get(fileName);
        } catch (InvalidPathException e) {
            System.err.println("Некорректный путь к файлу: " + fileName);
            return new ArrayList<>();
        }

        if (!Files.exists(path)) {
            System.err.println("Файл " + fileName + " не найден!");
            return new ArrayList<>();
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            List<CarRawData> rawData = gson.fromJson(reader, new TypeToken<List<CarRawData>>() {
            }.getType());

            if (rawData == null) return new ArrayList<>();

            return rawData.stream()
                    .limit(count)
                    .map(raw -> Car.builder()
                            .model(raw.model)
                            .power(raw.power)
                            .year(raw.year)
                            .build())
                    .toList();
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Ошибка чтения/обработки файла: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static class CarRawData {
        String model;
        int power;
        int year;
    }
}
