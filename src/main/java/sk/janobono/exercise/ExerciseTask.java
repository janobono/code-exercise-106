package sk.janobono.exercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class ExerciseTask {

    public void execute(final Path inputDataPath, final boolean skipHeader) {
        final LineParser csvService = new LineParser();
        final ReportService reportService = new ReportService();

        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(inputDataPath.toFile()))) {
            String line;
            int lineNumber = 0;

            if (skipHeader) {
                line = bufferedReader.readLine();
                if (line == null) {
                    return;
                }
                lineNumber++;
            }

            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                final LineDto lineData = csvService.parseLine(line, lineNumber);
                reportService.addEmployee(lineData);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        reportService.printEarnLessReport();
        reportService.printEarnMoreReport();
        reportService.printTooLongLineReport();
    }
}
