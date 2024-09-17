package qa.project.consultation_scheduler.course.infrastructure.request;

import jakarta.validation.constraints.NotNull;
import qa.project.consultation_scheduler.course.domain.entity.Semester;
import qa.project.consultation_scheduler.course.domain.factory.SemesterFactory;

import java.time.LocalDate;

public record CreateSemesterReq(@NotNull(message = "Start date is required") LocalDate startDate,
                                @NotNull(message = "End date is required") LocalDate endDate) {

    public CreateSemesterReq {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    public Semester toEntity() {
        return SemesterFactory.create(startDate, endDate);
    }
}
