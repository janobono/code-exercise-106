package sk.janobono.exercise;

import sk.janobono.ApplicationException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class ReportRepository {

    private static final int MIN_PERCENTAGE = 20;
    private static final int MAX_PERCENTAGE = 50;
    private static final int MAX_LINE_LENGTH = 4;
    private static final String EARN_LESS = "earn_less.txt";
    private static final String EARN_MORE = "earn_more.txt";
    private static final String LONG_LINE = "long_line.txt";

    private final Path repositoryDir;

    public ReportRepository() {
        try {
            repositoryDir = Files.createTempDirectory("Exercise106");
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmployee(final LineDto lineData) {
        if (Files.exists(repositoryDir.resolve(getFileName(lineData.id())))) {
            throw new ApplicationException("Duplicated line [%d].".formatted(lineData.lineNumber()));
        }

        if (lineData.managerId() == null) {
            final ReportEmployeeDto reportEmployeeDto = new ReportEmployeeDto(lineData, 0, 0, 0);
            save(reportEmployeeDto);
            return;
        }

        if (!Files.exists(repositoryDir.resolve(getFileName(lineData.managerId())))) {
            throw new ApplicationException("Manager not found line [%d].".formatted(lineData.lineNumber()));
        }

        ReportEmployeeDto manager = load(lineData.managerId());
        final ReportEmployeeDto employee = new ReportEmployeeDto(lineData, 0, 0, manager.lineLength() + 1);
        manager = new ReportEmployeeDto(
                manager.lineDto(),
                manager.salarySum() + employee.lineDto().salary(),
                manager.subordinatesCount() + 1,
                manager.lineLength());

        save(employee);
        save(manager);

        final int averageSalary = manager.salarySum() / manager.subordinatesCount();
        final int percentage = calculatePercentage(manager.lineDto().salary(), averageSalary);

        if (percentage < 100 + MIN_PERCENTAGE) {
            addLine(EARN_LESS, manager.lineDto().id());
        }

        if (percentage > 100 + MAX_PERCENTAGE) {
            addLine(EARN_MORE, manager.lineDto().id());
        }

        if (employee.lineLength() > MAX_LINE_LENGTH) {
            addLine(LONG_LINE, employee.lineDto().id());
        }
    }

    public Set<ReportDto> findAllManagersEarnLess() {
        return findAllManagers(EARN_LESS);
    }

    public Set<ReportDto> findAllManagersEarnMore() {
        return findAllManagers(EARN_MORE);
    }

    public Set<ReportDto> findAllEmployeesLongLine() {
        final Set<ReportDto> result = new HashSet<>();
        if (Files.exists(repositoryDir.resolve(LONG_LINE))) {
            try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(repositoryDir.resolve(LONG_LINE).toFile()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    final ReportEmployeeDto reportEmployeeDto = load(Integer.parseInt(line));

                    result.add(new ReportDto(
                            reportEmployeeDto.lineDto().firstName(),
                            reportEmployeeDto.lineDto().lastName(),
                            reportEmployeeDto.lineLength() - MAX_LINE_LENGTH
                    ));
                }
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private String getFileName(final int id) {
        return "employee[%d].bin".formatted(id);
    }

    private ReportEmployeeDto load(final int id) {
        return load(repositoryDir.resolve(getFileName(id)));
    }

    private <T> T load(final Path path) {
        try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return (T) ois.readObject();
        } catch (final IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(final ReportEmployeeDto reportEmployeeDto) {
        save(repositoryDir.resolve(getFileName(reportEmployeeDto.lineDto().id())), reportEmployeeDto);
    }

    private <T> void save(final Path path, final T data) {
        try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(data);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int calculatePercentage(final int obtained, final int total) {
        return obtained * 100 / total;
    }

    private void addLine(final String fileName, final int id) {
        try (final PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(repositoryDir.resolve(fileName).toFile(), true)))) {
            writer.println(Integer.toString(id));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<ReportDto> findAllManagers(final String fileName) {
        final Set<ReportDto> result = new HashSet<>();
        if (Files.exists(repositoryDir.resolve(fileName))) {
            try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(repositoryDir.resolve(fileName).toFile()))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    final ReportEmployeeDto reportEmployeeDto = load(Integer.parseInt(line));

                    final int averageSalary = reportEmployeeDto.salarySum() / reportEmployeeDto.subordinatesCount();

                    result.add(new ReportDto(
                            reportEmployeeDto.lineDto().firstName(),
                            reportEmployeeDto.lineDto().lastName(),
                            Math.abs(averageSalary - reportEmployeeDto.lineDto().salary())
                    ));
                }
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
