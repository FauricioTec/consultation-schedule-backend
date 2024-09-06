package qa.project.consultation_scheduler.course.domain.usecase;

import qa.project.consultation_scheduler.course.domain.entity.Course;

public interface CreateCourseUseCase {

    Course createCourse(Course course);
}
