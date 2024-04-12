package sk.janobono.exercise.csv;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvLineReaderTest {

    @Test
    void readLine_emptyCsv_ZeroLines() throws Exception {
        try (final CsvLineReader csvLineReader = new CsvLineReader(Paths.get("src", "test", "resources", "empty.csv"), true)) {
            final var line = csvLineReader.readLine();
            assertTrue(line.isEmpty());
        }
    }

    @Test
    void readLine_testCsv_6Lines() throws Exception {
        int lineNumber = 0;

        try (final CsvLineReader csvLineReader = new CsvLineReader(Paths.get("src", "test", "resources", "test.csv"), true)) {
            Optional<CsvLineDto> line;
            while ((line = csvLineReader.readLine()).isPresent()) {
                lineNumber = line.get().lineNumber();
            }
        }

        assertEquals(lineNumber, 6);
    }

    @Test
    void readLine_noHeaderCsv_5Lines() throws Exception {
        int lineNumber = 0;

        try (final CsvLineReader csvLineReader = new CsvLineReader(Paths.get("src", "test", "resources", "no_header.csv"), false)) {
            Optional<CsvLineDto> line;
            while ((line = csvLineReader.readLine()).isPresent()) {
                lineNumber = line.get().lineNumber();
            }
        }

        assertEquals(lineNumber, 5);
    }
}
