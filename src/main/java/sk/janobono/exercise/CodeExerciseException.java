package sk.janobono.exercise;

public class CodeExerciseException extends RuntimeException {

    public CodeExerciseException(final String message) {
        super(message);
    }

    public CodeExerciseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
