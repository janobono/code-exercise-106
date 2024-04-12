package sk.janobono;

import sk.janobono.exercise.ExerciseTask;

public class Application {

    public static void main(final String[] args) {
        try {
            final ApplicationArguments applicationArgumentsParser = new ApplicationArguments(args);
            new ExerciseTask().execute(
                    applicationArgumentsParser.getInputDataPath(),
                    applicationArgumentsParser.getSkipHeader()
            );
            System.exit(0);
        } catch (final ApplicationException applicationException) {
            System.err.println(applicationException.getMessage());
            if (applicationException.getCause() != null) {
                System.err.printf("%s%n", applicationException.getCause());
            }
            System.exit(1);
        } catch (final Exception e) {
            System.err.printf("Something went wrong. %s%n", e);
            System.exit(1);
        }
    }
}
