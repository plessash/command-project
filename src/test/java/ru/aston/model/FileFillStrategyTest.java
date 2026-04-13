package ru.aston.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.aston.service.strategy.impl.FileFillStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileFillStrategyTest {

    @TempDir
    Path tempDir;

    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = tempDir.resolve("test_cars.json");
        String defaultJson = """
                [
                    { "model": "Aurus Komendant", "power": 598, "year": 2023},
                    { "model": "Lada Granta Drive Active", "power": 106, "year": 2024},
                    { "model": "GAZ Volga Siber", "power": 143, "year": 2010},
                    { "model": "Moskvich 3e", "power": 193, "year": 2023},
                    { "model": "Shortcut (527 Edition)", "power": 184, "year": 2023}
                ]
                """;
        Files.writeString(tempFile, defaultJson);
    }

    @Test
    void shouldReturnCorrectNumberOfCarsFromFile() {
        int requestedCount = 2;
        List<Car> result = new FileFillStrategy(tempFile.toString()).fill(requestedCount);

        assertAll(
                () -> assertEquals(requestedCount, result.size()),
                () -> assertEquals("Aurus Komendant", result.getFirst().getModel()),
                () -> assertEquals(598, result.getFirst().getPower()),
                () -> assertEquals(2023, result.getFirst().getYear()),
                () -> assertEquals("Lada Granta Drive Active", result.get(1).getModel()),
                () -> assertEquals(106, result.get(1).getPower()),
                () -> assertEquals(2024, result.get(1).getYear())
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "other.json", " ", "some/file.txt"})
    void shouldReturnEmptyListIfFileNotFound(String invalidPath) {
        List<Car> result = new FileFillStrategy(invalidPath).fill(5);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListIfFileJsonIsEmpty() throws IOException {
        Path emptyFile = tempDir.resolve("empty.json");
        Files.writeString(emptyFile, "[]");
        List<Car> result = new FileFillStrategy(emptyFile.toString()).fill(5);

        assertTrue(result.isEmpty());
    }

    @ParameterizedTest(name = "Запрос {0} машин(ы) должен вернуть {1}")
    @CsvSource({
            "1, 1",
            "2, 2",
            "3, 3",
            "4, 4",
            "5, 5",
            "10, 5",
            "0, 0"
    })
    void shouldReturnCorrectSizeForDifferentLimits(int requestedCount, int expectedSize) {
        List<Car> result = new FileFillStrategy(tempFile.toString()).fill(requestedCount);

        assertEquals(expectedSize, result.size());
    }

    @Test
    void shouldThrowExceptionWhenCountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new FileFillStrategy(tempFile.toString()).fill(-1));
    }

    @Test
    void shouldReturnEmptyListIfJsonIsInvalid() throws IOException {
        Path corruptFile = tempDir.resolve("corrupt_cars.json");
        Files.writeString(corruptFile, "Это не JSON, а просто текст { model: 123 }");
        List<Car> result = new FileFillStrategy(corruptFile.toString()).fill(5);

        assertTrue(result.isEmpty());
    }
}
