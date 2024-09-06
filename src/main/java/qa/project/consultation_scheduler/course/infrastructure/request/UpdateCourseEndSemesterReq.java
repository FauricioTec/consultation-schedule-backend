package qa.project.consultation_scheduler.course.infrastructure.request;

import java.time.LocalDate;

public record UpdateCourseEndSemesterReq(LocalDate endDate) {
}
