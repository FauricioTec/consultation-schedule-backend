package qa.project.consultation_scheduler.course.aplication.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.course.domain.usecase.UpdateCourseSemesterEndDateUseCase;

import java.time.LocalDate;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateCourseSemesterEndDateUseCaseImpl implements UpdateCourseSemesterEndDateUseCase {

    private final CourseRepository courseRepository;

    @Override
    public Course updateCourseSemesterEndDate(UUID courseId, LocalDate semesterEndDate) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
        try {
            course.updateSemesterEndDate(semesterEndDate);
            return courseRepository.save(course);
        } catch (Exception e) {
            throw new RuntimeException("Error updating course semester end date");
        }
    }
}
