package sk.janobono.exercise.csv;

import java.io.Serializable;

public record CsvLineDto(
        int lineNumber,
        int id,
        String firstName,
        String lastName,
        int salary,
        Integer managerId
) implements Serializable {
}
