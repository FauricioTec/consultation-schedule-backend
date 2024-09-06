package qa.project.consultation_scheduler.course.aplication.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.course.domain.usecase.GetCourseByIdUseCase;

import java.util.UUID;

@Component
@AllArgsConstructor
public class GetCourseByIdUseCaseImpl implements GetCourseByIdUseCase {

    private final CourseRepository courseRepository;

    @Override
    public Course getCourseById(UUID courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
    }

}
