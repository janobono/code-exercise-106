package sk.janobono.exercise;

import sk.janobono.ApplicationException;
import sk.janobono.exercise.csv.CsvLineDto;
import sk.janobono.exercise.csv.CsvLineReader;
import sk.janobono.exercise.report.ReportLineDto;
import sk.janobono.exercise.report.ReportLineReader;
import sk.janobono.exercise.report.ReportRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class ExerciseTask {

    public void execute(final Path inputDataPath, final boolean skipHeader) {
        try (
                final CsvLineReader lineReader = new CsvLineReader(inputDataPath, skipHeader);
                final ReportRepository reportRepository = new ReportRepository(Files.createTempDirectory("Exercise106"));
        ) {
            Optional<CsvLineDto> line;

            while ((line = lineReader.readLine()).isPresent()) {
                reportRepository.addEmployee(line.get());
            }

            printEarnLessReport(reportRepository);
            printEarnMoreReport(reportRepository);
            printTooLongLineReport(reportRepository);

        } catch (final ApplicationException applicationException) {
            throw applicationException;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void printEarnLessReport(final ReportRepository reportRepository) {
        try (final ReportLineReader reportReader = reportRepository.getEarnLessReader()) {
            Optional<ReportLineDto> report;
            while ((report = reportReader.readLine()).isPresent()) {
                final ReportLineDto reportDto = report.get();
                System.out.printf(
                        "Manager %s %s earns less than he should by [%d].%n",
                        reportDto.firstName(),
                        reportDto.lastName(),
                        reportDto.diff()
                );
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void printEarnMoreReport(final ReportRepository reportRepository) {
        try (final ReportLineReader reportReader = reportRepository.getEarnMoreReader()) {
            Optional<ReportLineDto> report;
            while ((report = reportReader.readLine()).isPresent()) {
                final ReportLineDto reportDto = report.get();
                System.out.printf(
                        "Manager %s %s earns more than he should by [%d].%n",
                        reportDto.firstName(),
                        reportDto.lastName(),
                        reportDto.diff()
                );
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void printTooLongLineReport(final ReportRepository reportRepository) {
        try (final ReportLineReader reportReader = reportRepository.getTooLongLineReader()) {
            Optional<ReportLineDto> report;
            while ((report = reportReader.readLine()).isPresent()) {
                final ReportLineDto reportDto = report.get();
                System.out.printf(
                        "Employee %s %s line is too long exceeds [%d].%n",
                        reportDto.firstName(),
                        reportDto.lastName(),
                        reportDto.diff()
                );
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
