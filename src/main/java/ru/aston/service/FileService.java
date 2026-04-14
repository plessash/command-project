package ru.aston.service;

import java.io.FileWriter;
import java.io.IOException;

public class FileService {

    public void appendToFile(String path, Iterable<?> data) {
        try (FileWriter writer = new FileWriter(path, true)) {
            for (Object obj : data) {
                writer.write(String.valueOf(obj));
                writer.write(System.lineSeparator());
            }
            writer.write("-----");
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл: " + e.getMessage(), e);
        }
    }
}