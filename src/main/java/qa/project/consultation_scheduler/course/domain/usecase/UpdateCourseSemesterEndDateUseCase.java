package qa.project.consultation_scheduler.course.domain.usecase;

import qa.project.consultation_scheduler.course.domain.entity.Course;

import java.time.LocalDate;
import java.util.UUID;

public interface UpdateCourseSemesterEndDateUseCase {

    Course updateCourseSemesterEndDate(UUID courseId, LocalDate semesterEndDate);
}
