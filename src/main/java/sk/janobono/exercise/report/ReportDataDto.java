package sk.janobono.exercise.report;

import sk.janobono.exercise.csv.CsvLineDto;

import java.io.Serializable;

public record ReportDataDto(
        CsvLineDto lineDto,
        int salarySum,
        int subordinatesCount,
        double averageSalary,
        double percentage,
        int lineLength,
        boolean earnLess,
        boolean earnMore
) implements Serializable {
}
