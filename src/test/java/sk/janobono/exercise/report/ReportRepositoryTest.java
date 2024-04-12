package sk.janobono.exercise.report;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.janobono.exercise.csv.CsvLineDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReportRepositoryTest {

    private Path dir;

    @BeforeEach
    void setUp() throws IOException {
        dir = Paths.get("target", "ReportRepositoryTest");
        Files.createDirectories(dir);
    }

    @AfterEach
    void tearDown() {
        if (dir != null) {
            ReportRepositoryIoUtil.deleteDirectory(dir.toFile());
        }
    }

    @Test
    void reportRepository_dataSet_expectedResults() throws IOException {
        final List<CsvLineDto> dataSet = List.of(
                new CsvLineDto(1, 1, "Ceo", "Division1", 119, null),
                new CsvLineDto(2, 2, "Jack", "Division1", 100, 1),
                new CsvLineDto(3, 3, "Jim", "Division1", 100, 1),
                new CsvLineDto(4, 4, "John", "Division1", 100, 1),

                new CsvLineDto(10, 10, "Ceo", "Division2", 151, null),
                new CsvLineDto(11, 11, "Jack", "Division2", 100, 10),
                new CsvLineDto(12, 12, "Jim", "Division2", 100, 10),
                new CsvLineDto(13, 13, "John", "Division2", 100, 10),

                new CsvLineDto(20, 20, "Ceo", "Division3", 120, null),
                new CsvLineDto(21, 21, "Jack01", "Division3", 100, 20),
                new CsvLineDto(22, 22, "Jack02", "Division3", 80, 21),
                new CsvLineDto(23, 23, "Jack03", "Division3", 60, 22),
                new CsvLineDto(24, 24, "Jack04", "Division3", 40, 23),
                new CsvLineDto(25, 25, "Jack05", "Division3", 30, 24)
        );
        final List<ReportLineDto> earnLess;
        final List<ReportLineDto> earnMore;
        final List<ReportLineDto> tooLongLine;
        try (final ReportRepository reportRepository = new ReportRepository(dir, false)) {
            for (final CsvLineDto csvLineDto : dataSet) {
                reportRepository.addEmployee(csvLineDto);
            }

            earnLess = readLines(reportRepository.getEarnLessReader());
            earnMore = readLines(reportRepository.getEarnMoreReader());
            tooLongLine = readLines(reportRepository.getTooLongLineReader());
        }

        assertNotNull(earnLess);
        assertEquals(1, earnLess.size());
        assertEquals("Ceo", earnLess.get(0).firstName());
        assertEquals("Division1", earnLess.get(0).lastName());
        assertEquals(1, earnLess.get(0).diff());

        assertNotNull(earnMore);
        assertEquals(1, earnMore.size());
        assertEquals("Ceo", earnMore.get(0).firstName());
        assertEquals("Division2", earnMore.get(0).lastName());
        assertEquals(1, earnMore.get(0).diff());

        assertNotNull(tooLongLine);
        assertEquals(1, tooLongLine.size());
        assertEquals("Jack05", tooLongLine.get(0).firstName());
        assertEquals("Division3", tooLongLine.get(0).lastName());
        assertEquals(1, tooLongLine.get(0).diff());
    }

    private List<ReportLineDto> readLines(final ReportLineReader reportLineReader) {
        final List<ReportLineDto> result = new ArrayList<>();
        Optional<ReportLineDto> line;
        while ((line = reportLineReader.readLine()).isPresent()) {
            result.add(line.get());
        }
        return result;
    }
}
