package sk.janobono.exercise.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class CsvLineReader implements AutoCloseable {

    private final BufferedReader bufferedReader;
    private final CsvLineParser csvLineParser;

    private int lineNumber = 0;

    public CsvLineReader(final Path inputDataPath, final boolean skipHeader) throws IOException {
        bufferedReader = new BufferedReader(new FileReader(inputDataPath.toFile()));
        if (skipHeader) {
            bufferedReader.readLine();
            lineNumber++;
        }

        csvLineParser = new CsvLineParser();
    }

    public Optional<CsvLineDto> readLine() {
        try {
            final String line = bufferedReader.readLine();
            if (line != null) {
                lineNumber++;
                final CsvLineDto lineData = csvLineParser.parseLine(line, lineNumber);
                return Optional.of(lineData);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void close() throws Exception {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }
}
