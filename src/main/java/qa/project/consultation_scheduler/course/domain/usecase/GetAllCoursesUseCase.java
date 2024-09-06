package qa.project.consultation_scheduler.course.domain.usecase;

import qa.project.consultation_scheduler.course.domain.entity.Course;

import java.util.List;

public interface GetAllCoursesUseCase {

    List<Course> getAllCourses();
}
