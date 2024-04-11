package sk.janobono.exercise;

import java.nio.file.Path;

public record CodeExerciseProperties(
        Path inputData
) {

    public static CodeExerciseProperties parseApplicationProperties(final String[] args) {
        if (args.length != 1) {
            throw new CodeExerciseException("Input data path argument not found.");
        }

        final Path inputData = Path.of(args[0]);

        if (!inputData.toFile().exists()) {
            throw new CodeExerciseException("Input data path file not found.");
        }

        if (!inputData.toFile().isFile()) {
            throw new CodeExerciseException("Input data path is not file.");
        }

        return new CodeExerciseProperties(inputData);
    }
}
