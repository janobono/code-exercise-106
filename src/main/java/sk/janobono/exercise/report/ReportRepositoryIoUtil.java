package sk.janobono.exercise.report;

import sk.janobono.ApplicationException;

import java.io.*;
import java.nio.file.Path;

public class ReportRepositoryIoUtil {

    public static void cleanDirectory(final File dir) {
        final File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (final File file : allContents) {
                deleteDirectory(file);
            }
        }
    }

    public static void deleteDirectory(final File dir) {
        cleanDirectory(dir);
        dir.delete();
    }

    public static Path getReportDataFilePath(final Path dir, final int id) {
        return dir.resolve("employee[%d].bin".formatted(id));
    }

    public static <T> T load(final Path path) {
        try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return (T) ois.readObject();
        } catch (final IOException | ClassNotFoundException e) {
            throw new ApplicationException("Data read error.", e);
        }
    }

    public static <T> void save(final Path path, final T data) {
        try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(data);
            oos.flush();
        } catch (final IOException e) {
            throw new ApplicationException("Data write error.", e);
        }
    }

    public static void addLine(final Path dir, final String fileName, final int id) {
        try (final PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(dir.resolve(fileName).toFile(), true)))) {
            writer.println(id);
            writer.flush();
        } catch (final IOException e) {
            throw new ApplicationException("Line write error.", e);
        }
    }
}
