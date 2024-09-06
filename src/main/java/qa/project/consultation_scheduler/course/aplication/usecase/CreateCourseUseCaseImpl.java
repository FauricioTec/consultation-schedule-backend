package qa.project.consultation_scheduler.course.aplication.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.course.domain.usecase.CreateCourseUseCase;

@Component
@AllArgsConstructor
public class CreateCourseUseCaseImpl implements CreateCourseUseCase {

    private final CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
      return courseRepository.save(course);
    }
}
