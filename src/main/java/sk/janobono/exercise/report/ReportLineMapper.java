package sk.janobono.exercise.report;

@FunctionalInterface
public interface ReportLineMapper {
    ReportLineDto toReportLineDto(final ReportDataDto reportDataDto);
}
