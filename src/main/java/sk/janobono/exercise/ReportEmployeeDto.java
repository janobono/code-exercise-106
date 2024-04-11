package sk.janobono.exercise;

import java.io.Serializable;

public record ReportEmployeeDto(
        LineDto lineDto, int salarySum, int subordinatesCount, int lineLength
) implements Serializable {
}
