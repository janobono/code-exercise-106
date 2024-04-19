package sk.janobono.exercise.report;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportRepositoryMathUtilTest {

    @ParameterizedTest()
    @CsvSource({"0,0.1", "0,0.2", "0,0.3", "0,0.4", "1,0.5", "1,0.6", "1,0.7", "1,0.8", "1,0.9"})
    void toInt_ValidData_ExpectThisResults(final int expected, final double value) {
        assertEquals(expected, ReportRepositoryMathUtil.toInt(value));
    }

    @ParameterizedTest()
    @CsvSource({"10,100,10", "9,100,11", "8,100,12", "8,100,12", "8,100,13", "7,100,14", "7,100,15", "6,100,16", "6,100,17", "6,100,18", "5,100,19", "5,100,20"})
    void countAverageSalary_ValidData_ExpectThisResults(final int expected, final int salarySum, final int subordinatesCount) {
        assertEquals(expected, ReportRepositoryMathUtil.countAverageSalary(salarySum, subordinatesCount));
    }

    @ParameterizedTest()
    @CsvSource({"50,50,100", "60,60,100", "70,70,100", "80,80,100", "90,90,100", "100,100,100", "110,110,100", "120,120,100", "130,130,100", "140,140,100", "150,150,100"})
    void countPercentage_ValidData_ExpectThisResults(final int expected, final int salary, final int averageSalary) {
        assertEquals(expected, ReportRepositoryMathUtil.countPercentage(salary, averageSalary));
    }

    @ParameterizedTest()
    @CsvSource({"1,100,119,20", "1,100,151,50"})
    void countDiff_ValidData_ExpectThisResults(final int expected, final int averageSalary, final int salary, final int percentageDiff) {
        assertEquals(expected, ReportRepositoryMathUtil.countDiff(averageSalary, salary, percentageDiff));
    }
}
