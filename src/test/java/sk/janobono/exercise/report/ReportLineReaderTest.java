package sk.janobono.exercise.report;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.janobono.ApplicationException;
import sk.janobono.exercise.csv.CsvLineDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReportLineReaderTest {

    private Path dir;

    @BeforeEach
    void setUp() throws IOException {
        dir = Paths.get("target", "ReportLineReaderTest");
        Files.createDirectories(dir);
    }

    @AfterEach
    void tearDown() {
        if (dir != null) {
            ReportRepositoryIoUtil.deleteDirectory(dir.toFile());
        }
    }

    @Test
    void reportLineReader_fileNotExists_ExceptionIsThrown() {
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                () -> new ReportLineReader(dir, "none.txt", reportDataDto -> null));
    }

    @Test
    void readLine_EmptyLineFile_EmptyResult() throws Exception {
        Files.createFile(dir.resolve("line.txt"));
        final Optional<ReportLineDto> line;
        try (final ReportLineReader reportLineReader = new ReportLineReader(
                dir,
                "line.txt",
                reportDataDto -> new ReportLineDto(
                        reportDataDto.lineDto().firstName(),
                        reportDataDto.lineDto().lastName(),
                        reportDataDto.lineDto().lineNumber()
                )
        )) {
            line = reportLineReader.readLine();
        }
        assertFalse(line.isPresent());
    }

    @Test
    void readLine_NoDataFile_ExceptionIsThrown() throws Exception {
        ReportRepositoryIoUtil.addLine(dir, "line.txt", 1);
        try (final ReportLineReader reportLineReader = new ReportLineReader(
                dir,
                "line.txt",
                reportDataDto -> new ReportLineDto(
                        reportDataDto.lineDto().firstName(),
                        reportDataDto.lineDto().lastName(),
                        reportDataDto.lineDto().lineNumber()
                )
        )) {
            final RuntimeException runtimeException = assertThrows(RuntimeException.class, reportLineReader::readLine);
        }
    }

    @Test
    void readLine_ValidData_ValidResult() throws Exception {
        ReportRepositoryIoUtil.addLine(dir, "line.txt", 1);
        ReportRepositoryIoUtil.save(
                ReportRepositoryIoUtil.getReportDataFilePath(dir, 1),
                new ReportDataDto(
                        new CsvLineDto(
                                1,
                                1,
                                "firstName",
                                "lastName",
                                0,
                                null
                        ),
                        0,
                        0,
                        0,
                        0,
                        0,
                        false,
                        false
                )
        );
        final Optional<ReportLineDto> line;
        try (final ReportLineReader reportLineReader = new ReportLineReader(
                dir,
                "line.txt",
                reportDataDto -> new ReportLineDto(
                        reportDataDto.lineDto().firstName(),
                        reportDataDto.lineDto().lastName(),
                        reportDataDto.lineDto().lineNumber()
                )
        )) {
            line = reportLineReader.readLine();
        }
        assertTrue(line.isPresent());
    }
}
