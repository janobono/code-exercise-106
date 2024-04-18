package sk.janobono.exercise.report;

import sk.janobono.ApplicationException;
import sk.janobono.exercise.csv.model.CsvLineDto;
import sk.janobono.exercise.report.model.ReportDataDto;
import sk.janobono.exercise.report.model.ReportLineDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReportRepository implements AutoCloseable {

    private static final int MIN_PERCENTAGE = 20;
    private static final int MAX_PERCENTAGE = 50;
    private static final int MAX_LINE_LENGTH = 4;

    private static final String EARN_LESS = "earn_less.txt";
    private static final String EARN_MORE = "earn_more.txt";
    private static final String LONG_LINE = "long_line.txt";

    private final Path repositoryDir;

    public ReportRepository(final Path repositoryDir) {
        try {
            this.repositoryDir = repositoryDir;
            ReportRepositoryIoUtil.cleanDirectory(repositoryDir.toFile());
            Files.createFile(repositoryDir.resolve(EARN_LESS));
            Files.createFile(repositoryDir.resolve(EARN_MORE));
            Files.createFile(repositoryDir.resolve(LONG_LINE));
        } catch (final IOException e) {
            throw new ApplicationException("Report repository create error.", e);
        }
    }

    public void addEmployee(final CsvLineDto csvLineDto) {
        final Path employeePath = ReportRepositoryIoUtil.getReportDataFilePath(repositoryDir, csvLineDto.id());
        if (Files.exists(employeePath)) {
            throw new ApplicationException("Duplicated line [%d].".formatted(csvLineDto.lineNumber()));
        }

        if (csvLineDto.managerId() == null) {
            final ReportDataDto reportDataDto = new ReportDataDto(csvLineDto, 0, 0, 0, 0, false, false);
            ReportRepositoryIoUtil.save(employeePath, reportDataDto);
            return;
        }

        final Path managerPath = ReportRepositoryIoUtil.getReportDataFilePath(repositoryDir, csvLineDto.managerId());
        if (!Files.exists(managerPath)) {
            throw new ApplicationException("Manager not found line [%d].".formatted(csvLineDto.lineNumber()));
        }

        final ReportDataDto originalManager = ReportRepositoryIoUtil.load(managerPath);
        saveEmployee(employeePath, csvLineDto, originalManager);
        saveManager(managerPath, csvLineDto, originalManager);
    }

    public ReportLineReader getEarnLessReader() {
        return new ReportLineReader(repositoryDir, EARN_LESS, reportDataDto -> new ReportLineDto(
                reportDataDto.lineDto().firstName(),
                reportDataDto.lineDto().lastName(),
                ReportRepositoryMathUtil.countDiff(reportDataDto.averageSalary(), reportDataDto.lineDto().salary(), MIN_PERCENTAGE)
        ));
    }

    public ReportLineReader getEarnMoreReader() {
        return new ReportLineReader(repositoryDir, EARN_MORE, reportDataDto -> new ReportLineDto(
                reportDataDto.lineDto().firstName(),
                reportDataDto.lineDto().lastName(),
                ReportRepositoryMathUtil.countDiff(reportDataDto.averageSalary(), reportDataDto.lineDto().salary(), MAX_PERCENTAGE)
        ));
    }

    public ReportLineReader getTooLongLineReader() {
        return new ReportLineReader(repositoryDir, LONG_LINE, reportDataDto -> new ReportLineDto(
                reportDataDto.lineDto().firstName(),
                reportDataDto.lineDto().lastName(),
                reportDataDto.lineLength() - MAX_LINE_LENGTH
        ));
    }

    @Override
    public void close() {
        if (repositoryDir != null && Files.exists(repositoryDir)) {
            ReportRepositoryIoUtil.deleteDirectory(repositoryDir.toFile());
        }
    }

    private void saveEmployee(final Path employeePath, final CsvLineDto csvLineDto, final ReportDataDto manager) {
        final ReportDataDto employee = new ReportDataDto(csvLineDto, 0, 0, 0, manager.lineLength() + 1, false, false);
        ReportRepositoryIoUtil.save(employeePath, employee);

        if (employee.lineLength() > MAX_LINE_LENGTH) {
            ReportRepositoryIoUtil.addLine(repositoryDir, LONG_LINE, employee.lineDto().id());
        }
    }

    private void saveManager(final Path managerPath, final CsvLineDto csvLineDto, final ReportDataDto originalManager) {
        final int salarySum = originalManager.salarySum() + csvLineDto.salary();

        final int subordinatesCount = originalManager.subordinatesCount() + 1;

        final int averageSalary = ReportRepositoryMathUtil.countAverageSalary(salarySum, subordinatesCount);

        final int percentage = ReportRepositoryMathUtil.countPercentage(originalManager.lineDto().salary(), averageSalary);

        boolean earnLess = originalManager.earnLess();
        if (percentage - 100 < MIN_PERCENTAGE) {
            if (!earnLess) {
                ReportRepositoryIoUtil.addLine(repositoryDir, EARN_LESS, originalManager.lineDto().id());
                earnLess = true;
            }
        }

        boolean earnMore = originalManager.earnMore();
        if (percentage - 100 > MAX_PERCENTAGE) {
            if (!earnMore) {
                ReportRepositoryIoUtil.addLine(repositoryDir, EARN_MORE, originalManager.lineDto().id());
                earnMore = true;
            }
        }

        final ReportDataDto newManager = new ReportDataDto(
                originalManager.lineDto(),
                salarySum,
                subordinatesCount,
                averageSalary,
                originalManager.lineLength(),
                earnLess,
                earnMore
        );

        ReportRepositoryIoUtil.save(managerPath, newManager);
    }
}
