package sk.janobono;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class ApplicationArguments {

    private final String[] args;

    public ApplicationArguments(final String[] args) {
        this.args = Optional.ofNullable(args).orElse(new String[0]);
    }

    public Path getInputDataPath() {
        if (args.length == 0) {
            throw new ApplicationException("Input data path argument not found.");
        }

        final Path inputData = Path.of(args[0]);

        if (!Files.exists(inputData)) {
            throw new ApplicationException("Input data path file not found.");
        }

        if (Files.isDirectory(inputData)) {
            throw new ApplicationException("Input data path is not file.");
        }

        return inputData;
    }

    public boolean getSkipHeader() {
        if (args.length < 2) {
            System.out.println("Skip header argument is missing. Defaulting to true.");
            return true;
        }
        return Boolean.parseBoolean(args[1]);
    }
}
