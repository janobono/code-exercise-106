package sk.janobono;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationArgumentsTest {

    @Test
    void getInputDataPath_NullArguments_ThrowsApplicationException() {
        final ApplicationArguments applicationArguments = new ApplicationArguments(null);
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                applicationArguments::getInputDataPath);
        assertEquals("Input data path argument not found.", applicationException.getMessage());
    }

    @Test
    void getInputDataPath_EmptyArguments_ThrowsApplicationException() {
        final ApplicationArguments applicationArguments = new ApplicationArguments(new String[]{});
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                applicationArguments::getInputDataPath);
        assertEquals("Input data path argument not found.", applicationException.getMessage());
    }

    @Test
    void getInputDataPath_FileNotExistsArguments_ThrowsApplicationException() {
        final ApplicationArguments applicationArguments = new ApplicationArguments(new String[]{"data.csv"});
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                applicationArguments::getInputDataPath);
        assertEquals("Input data path file not found.", applicationException.getMessage());
    }

    @Test
    void getInputDataPath_DirectoryArguments_ThrowsApplicationException() {
        final ApplicationArguments applicationArguments = new ApplicationArguments(new String[]{"src"});
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                applicationArguments::getInputDataPath);
        assertEquals("Input data path is not file.", applicationException.getMessage());
    }

    @Test
    void getInputDataPath_ValidArguments_ReturnsInputDataPath() {
        final ApplicationArguments applicationArguments = new ApplicationArguments(
                new String[]{"src/test/resources/test.csv"});
        assertEquals(Paths.get("src", "test", "resources", "test.csv"),
                applicationArguments.getInputDataPath());
    }

    @Test
    void getSkipHeader_NullArguments_ReturnsTrue() {
        final ApplicationArguments applicationArguments = new ApplicationArguments(null);
        assertTrue(applicationArguments.getSkipHeader());
    }

    @Test
    void getSkipHeader_EmptyArguments_ReturnsTrue() {
        final ApplicationArguments applicationArguments = new ApplicationArguments(new String[]{});
        assertTrue(applicationArguments.getSkipHeader());
    }

    @Test
    void getSkipHeader_Anything_ReturnsFalse() {
        final ApplicationArguments applicationArguments = new ApplicationArguments(new String[]{"", "anything"});
        assertFalse(applicationArguments.getSkipHeader());
    }

    @Test
    void getSkipHeader_ValidInput_ReturnsTrue() {
        final ApplicationArguments applicationArguments = new ApplicationArguments(new String[]{"", "true"});
        assertTrue(applicationArguments.getSkipHeader());
    }
}
