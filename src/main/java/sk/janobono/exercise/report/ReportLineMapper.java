package sk.janobono.exercise.report;

import sk.janobono.exercise.report.model.ReportDataDto;
import sk.janobono.exercise.report.model.ReportLineDto;

@FunctionalInterface
public interface ReportLineMapper {
    ReportLineDto toReportLineDto(final ReportDataDto reportDataDto);
}
