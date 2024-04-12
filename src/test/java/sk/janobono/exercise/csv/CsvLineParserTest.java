package sk.janobono.exercise.csv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.janobono.ApplicationException;

import static org.junit.jupiter.api.Assertions.*;

class CsvLineParserTest {

    CsvLineParser csvLineParser;

    @BeforeEach
    void setUp() {
        csvLineParser = new CsvLineParser();
    }

    @Test
    void parseLine_NullLine_ThrowsApplicationException() {
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                () -> csvLineParser.parseLine(null, 1));
        assertEquals("Invalid employee at line [1]: null", applicationException.getMessage());
    }

    @Test
    void parseLine_EmptyLine_ThrowsApplicationException() {
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                () -> csvLineParser.parseLine("", 1));
        assertEquals("Invalid employee at line [1]: ", applicationException.getMessage());
    }

    @Test
    void parseLine_ShortLine_ThrowsApplicationException() {
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                () -> csvLineParser.parseLine("1,test,invalid", 1));
        assertEquals("Invalid employee at line [1]: 1,test,invalid", applicationException.getMessage());
    }

    @Test
    void parseLine_WrongNumberFormatLine_ThrowsApplicationException() {
        final ApplicationException applicationException = assertThrows(ApplicationException.class,
                () -> csvLineParser.parseLine("1,test,invalid,10.2,2", 1));
        assertEquals("Invalid employee at line [1]: 1,test,invalid,10.2,2", applicationException.getMessage());
    }

    @Test
    void parseLine_CeoLine_ThrowsApplicationException() {
        final CsvLineDto csvLineDto = csvLineParser.parseLine("123,Joe,Doe,60000,", 1);
        assertEquals(123, csvLineDto.id());
        assertEquals("Joe", csvLineDto.firstName());
        assertEquals("Doe", csvLineDto.lastName());
        assertEquals(60000, csvLineDto.salary());
        assertNull(csvLineDto.managerId());
    }

    @Test
    void parseLine_EmployeeLine_ThrowsApplicationException() {
        final CsvLineDto csvLineDto = csvLineParser.parseLine("124,Martin,Chekov,45000,123", 1);
        assertEquals(124, csvLineDto.id());
        assertEquals("Martin", csvLineDto.firstName());
        assertEquals("Chekov", csvLineDto.lastName());
        assertEquals(45000, csvLineDto.salary());
        assertEquals(123, csvLineDto.managerId());
    }
}
