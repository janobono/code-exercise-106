package sk.janobono.exercise.csv;

import sk.janobono.ApplicationException;

import java.util.Optional;

public class CsvLineParser {

    public CsvLineDto parseLine(final String line, final int lineNumber) {
        final String[] elements = Optional.ofNullable(line)
                .orElse("")
                .strip()
                .split(",");

        try {
            if (elements.length < 4) {
                throw new IllegalArgumentException("Elements length must be at least 4");
            }

            final int salary = Integer.parseInt(elements[3]);
            if (salary <= 0) {
                throw new IllegalArgumentException("Salary must be at least 1");
            }

            return new CsvLineDto(
                    lineNumber,
                    Integer.parseInt(elements[0]),
                    elements[1].strip(),
                    elements[2].strip(),
                    salary,
                    elements.length == 5 ? Integer.parseInt(elements[4]) : null
            );
        } catch (final Exception e) {
            throw new ApplicationException("Invalid employee at line [%d]: %s".formatted(lineNumber, line), e);
        }
    }
}
