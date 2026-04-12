package ru.aston.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileServiceTest {

    private final FileService fileService = new FileService();

    @TempDir
    Path tempDir;

    @Test
    void appendToFile_shouldWriteAllItemsToFile() throws Exception {
        Path file = tempDir.resolve("result.txt");

        fileService.appendToFile(file.toString(), List.of("BMW", "Audi", "Tesla"));

        String content = Files.readString(file);

        String expected = String.join(System.lineSeparator(),
                "BMW",
                "Audi",
                "Tesla",
                "-----",
                ""
        );

        assertEquals(expected, content);
    }

    @Test
    void appendToFile_shouldAppendDataToExistingFile() throws Exception {
        Path file = tempDir.resolve("result.txt");

        fileService.appendToFile(file.toString(), List.of("BMW"));
        fileService.appendToFile(file.toString(), List.of("Audi"));

        String content = Files.readString(file);

        String expected = String.join(System.lineSeparator(),
                "BMW",
                "-----",
                "Audi",
                "-----",
                ""
        );

        assertEquals(expected, content);
    }

    @Test
    void appendToFile_shouldWriteSeparatorForEmptyCollection() throws Exception {
        Path file = tempDir.resolve("result.txt");

        fileService.appendToFile(file.toString(), List.of());

        String content = Files.readString(file);

        String expected = "-----" + System.lineSeparator();

        assertEquals(expected, content);
    }

    @Test
    void appendToFile_shouldThrowRuntimeExceptionWhenPathIsInvalid() {
        String invalidPath = tempDir.resolve("missing").resolve("result.txt").toString();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileService.appendToFile(invalidPath, List.of("BMW"))
        );

        assertTrue(exception.getMessage().contains("Ошибка записи в файл"));
    }
}