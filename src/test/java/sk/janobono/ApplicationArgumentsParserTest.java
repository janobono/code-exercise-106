package sk.janobono;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationArgumentsParserTest {

    @Test
    void getInputDataPath_NullArguments_ThrowsApplicationException() {
        final ApplicationArgumentsParser applicationArgumentsParser = new ApplicationArgumentsParser(null);
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                applicationArgumentsParser::getInputDataPath);
        assertEquals("Input data path argument not found.", applicationException.getMessage());
    }

    @Test
    void getInputDataPath_EmptyArguments_ThrowsApplicationException() {
        final ApplicationArgumentsParser applicationArgumentsParser = new ApplicationArgumentsParser(new String[]{});
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                applicationArgumentsParser::getInputDataPath);
        assertEquals("Input data path argument not found.", applicationException.getMessage());
    }

    @Test
    void getInputDataPath_FileNotExistsArguments_ThrowsApplicationException() {
        final ApplicationArgumentsParser applicationArgumentsParser = new ApplicationArgumentsParser(new String[]{"data.csv"});
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                applicationArgumentsParser::getInputDataPath);
        assertEquals("Input data path file not found.", applicationException.getMessage());
    }

    @Test
    void getInputDataPath_DirectoryArguments_ThrowsApplicationException() {
        final ApplicationArgumentsParser applicationArgumentsParser = new ApplicationArgumentsParser(new String[]{"src"});
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                applicationArgumentsParser::getInputDataPath);
        assertEquals("Input data path is not file.", applicationException.getMessage());
    }

    @Test
    void getInputDataPath_ValidArguments_ReturnsInputDataPath() {
        final ApplicationArgumentsParser applicationArgumentsParser = new ApplicationArgumentsParser(
                new String[]{"src/test/resources/test.csv"});
        assertEquals(Paths.get("src", "test", "resources", "test.csv"),
                applicationArgumentsParser.getInputDataPath());
    }

    @Test
    void getSkipHeader_NullArguments_ReturnsTrue() {
        final ApplicationArgumentsParser applicationArgumentsParser = new ApplicationArgumentsParser(null);
        assertTrue(applicationArgumentsParser.getSkipHeader());
    }

    @Test
    void getSkipHeader_EmptyArguments_ReturnsTrue() {
        final ApplicationArgumentsParser applicationArgumentsParser = new ApplicationArgumentsParser(new String[]{});
        assertTrue(applicationArgumentsParser.getSkipHeader());
    }

    @Test
    void getSkipHeader_Anything_ReturnsFalse() {
        final ApplicationArgumentsParser applicationArgumentsParser = new ApplicationArgumentsParser(new String[]{"", "anything"});
        assertFalse(applicationArgumentsParser.getSkipHeader());
    }

    @Test
    void getSkipHeader_ValidInput_ReturnsTrue() {
        final ApplicationArgumentsParser applicationArgumentsParser = new ApplicationArgumentsParser(new String[]{"", "true"});
        assertTrue(applicationArgumentsParser.getSkipHeader());
    }
}
