package qa.project.consultation_scheduler.course.application.usecase;

import lombok.AllArgsConstructor;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.course.domain.usecase.GetCourseByIdUseCase;

import java.util.UUID;

@AllArgsConstructor
public class GetCourseByIdUseCaseImpl implements GetCourseByIdUseCase {

    private final CourseRepository courseRepository;

    @Override
    public Course getCourseById(UUID courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
    }

}
