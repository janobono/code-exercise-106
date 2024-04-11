package sk.janobono.exercise;

import sk.janobono.ApplicationException;

import java.util.Optional;

public class LineParser {

    public LineDto parseLine(final String line, final int lineNumber) {
        final String[] elements = Optional.ofNullable(line)
                .orElse("")
                .strip()
                .split(",");

        try {
            if (elements.length < 4) {
                throw new IllegalArgumentException();
            }

            return new LineDto(
                    lineNumber,
                    Integer.parseInt(elements[0]),
                    elements[1].strip(),
                    elements[2].strip(),
                    Integer.parseInt(elements[3]),
                    elements.length == 5 ? Integer.parseInt(elements[4]) : null
            );
        } catch (final Exception e) {
            throw new ApplicationException("Invalid employee at line [%d]: %s".formatted(lineNumber, line), e);
        }
    }
}
