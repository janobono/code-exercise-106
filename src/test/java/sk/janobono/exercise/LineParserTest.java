package sk.janobono.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.janobono.ApplicationException;

import static org.junit.jupiter.api.Assertions.*;

class LineParserTest {

    LineParser lineParser;

    @BeforeEach
    void setUp() {
        lineParser = new LineParser();
    }

    @Test
    void parseLine_NullLine_ThrowsApplicationException() {
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                () -> lineParser.parseLine(null, 1));
        assertEquals("Invalid employee at line [1]: null", applicationException.getMessage());
    }

    @Test
    void parseLine_EmptyLine_ThrowsApplicationException() {
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                () -> lineParser.parseLine("", 1));
        assertEquals("Invalid employee at line [1]: ", applicationException.getMessage());
    }

    @Test
    void parseLine_ShortLine_ThrowsApplicationException() {
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                () -> lineParser.parseLine("1,test,invalid", 1));
        assertEquals("Invalid employee at line [1]: 1,test,invalid", applicationException.getMessage());
    }

    @Test
    void parseLine_WrongNumberFormatLine_ThrowsApplicationException() {
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                () -> lineParser.parseLine("1,test,invalid,10.2,2", 1));
        assertEquals("Invalid employee at line [1]: 1,test,invalid,10.2,2", applicationException.getMessage());
    }

    @Test
    void parseLine_CeoLine_ThrowsApplicationException() {
        final LineDto lineData = lineParser.parseLine("123,Joe,Doe,60000,", 1);
        assertEquals(123, lineData.id());
        assertEquals("Joe", lineData.firstName());
        assertEquals("Doe", lineData.lastName());
        assertEquals(60000, lineData.salary());
        assertNull(lineData.managerId());
    }

    @Test
    void parseLine_EmployeeLine_ThrowsApplicationException() {
        final LineDto lineData = lineParser.parseLine("124,Martin,Chekov,45000,123", 1);
        assertEquals(124, lineData.id());
        assertEquals("Martin", lineData.firstName());
        assertEquals("Chekov", lineData.lastName());
        assertEquals(45000, lineData.salary());
        assertEquals(123, lineData.managerId());
    }
}
