package qa.project.consultation_scheduler.course.domain.usecase;

import qa.project.consultation_scheduler.course.domain.entity.Course;

import java.util.UUID;

public interface GetCourseByIdUseCase {

    Course getCourseById(UUID courseId);
}
