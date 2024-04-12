package sk.janobono.exercise.report;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ReportRepositoryIoUtilTest {

    @Test
    void ioOperationsTest() throws Exception {
        final Path dir = Paths.get("target", "ReportRepositoryIoUtilTest");
        final Path dataFile = dir.resolve("test.bin");
        Files.createDirectories(dir);

        final Integer numberToSave = 10;
        ReportRepositoryIoUtil.save(dataFile, numberToSave);
        final Integer loadedNumber = ReportRepositoryIoUtil.load(dataFile);
        assertEquals(numberToSave, loadedNumber);

        ReportRepositoryIoUtil.addLine(dir, "test.txt", numberToSave);
        assertTrue(Files.exists(dir.resolve("test.txt")));

        ReportRepositoryIoUtil.cleanDirectory(dir.toFile());
        assertTrue(Files.exists(dir));
        assertFalse(Files.exists(dataFile));
        assertFalse(Files.exists(dir.resolve("test.txt")));

        ReportRepositoryIoUtil.deleteDirectory(dir.toFile());
        assertFalse(Files.exists(dir));
    }
}
