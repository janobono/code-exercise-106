package sk.janobono.exercise.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReportRepositoryMathUtil {

    public static int toInt(final double value) {
        return BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP).intValue();
    }

    public static int countAverageSalary(final int salarySum, final int subordinatesCount) {
        final double result = (double) salarySum / subordinatesCount;
        return toInt(result);
    }

    public static int countPercentage(final int salary, final int averageSalary) {
        return toInt((double) salary * 100 / averageSalary);
    }

    public static int countDiff(final double averageSalary, final int salary, final int percentageDiff) {
        final double onePerVal = averageSalary / 100;
        final double neededSalary = onePerVal * (100 + percentageDiff);
        return Math.abs(toInt(neededSalary) - salary);
    }

}
