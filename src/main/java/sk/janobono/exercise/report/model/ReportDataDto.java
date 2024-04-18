package sk.janobono.exercise.report.model;

import sk.janobono.exercise.csv.model.CsvLineDto;

import java.io.Serializable;

public record ReportDataDto(
        CsvLineDto lineDto,
        int salarySum,
        int subordinatesCount,
        double averageSalary,
        int lineLength,
        boolean earnLess,
        boolean earnMore
) implements Serializable {
}
