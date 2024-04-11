package sk.janobono.exercise;

public class ReportService {

    private final ReportRepository reportRepository = new ReportRepository();

    public void addEmployee(final LineDto lineData) {
        reportRepository.addEmployee(lineData);
    }

    public void printEarnLessReport() {
        reportRepository.findAllManagersEarnLess()
                .forEach(reportDto ->
                        System.out.printf(
                                "Manager %s %s earn less than he should of [%d].%n",
                                reportDto.firstName(),
                                reportDto.lastName(),
                                reportDto.diff()
                        ));
    }

    public void printEarnMoreReport() {
        reportRepository.findAllManagersEarnMore()
                .forEach(reportDto ->
                        System.out.printf(
                                "Manager %s %s earn more than he should of [%d].%n",
                                reportDto.firstName(),
                                reportDto.lastName(),
                                reportDto.diff()
                        ));
    }

    public void printTooLongLineReport() {
        reportRepository.findAllEmployeesLongLine()
                .forEach(reportDto ->
                        System.out.printf(
                                "Employee %s %s too long line exceeded of [%d].%n",
                                reportDto.firstName(),
                                reportDto.lastName(),
                                reportDto.diff()
                        ));
    }
}
