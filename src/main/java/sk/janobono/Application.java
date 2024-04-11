package sk.janobono;

import sk.janobono.exercise.CodeExercise;
import sk.janobono.exercise.CodeExerciseException;
import sk.janobono.exercise.CodeExerciseProperties;

public class Application {

    public static void main(final String[] args) {
        try {
            final CodeExerciseProperties codeExerciseProperties = CodeExerciseProperties.parseApplicationProperties(args);
            new CodeExercise(codeExerciseProperties).execute();
            System.exit(0);
        } catch (final CodeExerciseException applicationException) {
            System.err.println(applicationException.getMessage());
            System.exit(1);
        } catch (final Throwable e) {
            System.err.printf("Something went wrong. %s%n", e);
            System.exit(1);
        }
    }
}
