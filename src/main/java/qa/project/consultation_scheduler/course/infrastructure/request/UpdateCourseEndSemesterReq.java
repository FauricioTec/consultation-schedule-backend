package qa.project.consultation_scheduler.course.infrastructure.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateCourseEndSemesterReq(@NotNull(message = "End date is required") LocalDate endDate) {
}
