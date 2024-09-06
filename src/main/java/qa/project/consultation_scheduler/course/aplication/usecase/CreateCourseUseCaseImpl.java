package qa.project.consultation_scheduler.course.aplication.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.entity.Semester;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.course.domain.repository.SemesterRepository;
import qa.project.consultation_scheduler.course.domain.usecase.CreateCourseUseCase;

@Component
@AllArgsConstructor
public class CreateCourseUseCaseImpl implements CreateCourseUseCase {

    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;

    @Override
    public Course createCourse(Course course) {
        Semester semester = course.getSemester();
        semesterRepository.save(semester);
        return courseRepository.save(course);
    }
}
