package sk.janobono.exercise.report;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportRepositoryMathUtilTest {

    @Test
    void toInt_ValidData_ExpectThisResults() {
        assertEquals(0, ReportRepositoryMathUtil.toInt(0.1));
        assertEquals(0, ReportRepositoryMathUtil.toInt(0.2));
        assertEquals(0, ReportRepositoryMathUtil.toInt(0.3));
        assertEquals(0, ReportRepositoryMathUtil.toInt(0.4));
        assertEquals(1, ReportRepositoryMathUtil.toInt(0.5));
        assertEquals(1, ReportRepositoryMathUtil.toInt(0.6));
        assertEquals(1, ReportRepositoryMathUtil.toInt(0.7));
        assertEquals(1, ReportRepositoryMathUtil.toInt(0.8));
        assertEquals(1, ReportRepositoryMathUtil.toInt(0.9));
    }

    @Test
    void countAverageSalary_ValidData_ExpectThisResults() {
        assertEquals(10, ReportRepositoryMathUtil.countAverageSalary(100, 10));
        assertEquals(9, ReportRepositoryMathUtil.countAverageSalary(100, 11));
        assertEquals(8, ReportRepositoryMathUtil.countAverageSalary(100, 12));
        assertEquals(8, ReportRepositoryMathUtil.countAverageSalary(100, 13));
        assertEquals(7, ReportRepositoryMathUtil.countAverageSalary(100, 14));
        assertEquals(7, ReportRepositoryMathUtil.countAverageSalary(100, 15));
        assertEquals(6, ReportRepositoryMathUtil.countAverageSalary(100, 16));
        assertEquals(6, ReportRepositoryMathUtil.countAverageSalary(100, 17));
        assertEquals(6, ReportRepositoryMathUtil.countAverageSalary(100, 18));
        assertEquals(5, ReportRepositoryMathUtil.countAverageSalary(100, 19));
        assertEquals(5, ReportRepositoryMathUtil.countAverageSalary(100, 20));
    }

    @Test
    void countPercentage_ValidData_ExpectThisResults() {
        assertEquals(50, ReportRepositoryMathUtil.countPercentage(50, 100));
        assertEquals(60, ReportRepositoryMathUtil.countPercentage(60, 100));
        assertEquals(70, ReportRepositoryMathUtil.countPercentage(70, 100));
        assertEquals(80, ReportRepositoryMathUtil.countPercentage(80, 100));
        assertEquals(90, ReportRepositoryMathUtil.countPercentage(90, 100));
        assertEquals(100, ReportRepositoryMathUtil.countPercentage(100, 100));
        assertEquals(110, ReportRepositoryMathUtil.countPercentage(110, 100));
        assertEquals(120, ReportRepositoryMathUtil.countPercentage(120, 100));
        assertEquals(130, ReportRepositoryMathUtil.countPercentage(130, 100));
        assertEquals(140, ReportRepositoryMathUtil.countPercentage(140, 100));
        assertEquals(150, ReportRepositoryMathUtil.countPercentage(150, 100));
    }

    @Test
    void countDiff_ValidData_ExpectThisResults() {
        assertEquals(1, ReportRepositoryMathUtil.countDiff(100, 119, 20));
        assertEquals(1, ReportRepositoryMathUtil.countDiff(100, 151, 50));
    }
}
