package sk.janobono.exercise;

import java.io.Serializable;

public record LineDto(
        int lineNumber,
        int id,
        String firstName,
        String lastName,
        int salary,
        Integer managerId
) implements Serializable {
}
