package sk.janobono.exercise.report;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class ReportLineReader implements AutoCloseable {

    final Path dir;
    final BufferedReader bufferedReader;
    final ReportLineMapper reportLineMapper;

    public ReportLineReader(final Path dir, final String fileName, final ReportLineMapper reportLineMapper) throws FileNotFoundException {
        this.dir = dir;
        bufferedReader = new BufferedReader(new FileReader(dir.resolve(fileName).toFile()));
        this.reportLineMapper = reportLineMapper;
    }

    public Optional<ReportLineDto> readLine() {
        try {
            final String line = bufferedReader.readLine();
            if (line != null) {
                final ReportDataDto reportDataDto = ReportRepositoryIoUtil.load(
                        ReportRepositoryIoUtil.getReportDataFilePath(dir, Integer.parseInt(line)));
                return Optional.of(reportLineMapper.toReportLineDto(reportDataDto));
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
