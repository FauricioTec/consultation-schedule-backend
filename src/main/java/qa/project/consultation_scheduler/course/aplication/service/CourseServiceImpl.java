package qa.project.consultation_scheduler.course.aplication.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import qa.project.consultation_scheduler.course.aplication.usecase.*;
import qa.project.consultation_scheduler.course.domain.entity.Course;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final AddProfessorToCourseUseCaseImpl addProfessorToCourseUseCase;
    private final CreateCourseUseCaseImpl createCourseUseCase;
    private final GetAllCoursesUseCaseImpl getAllCoursesUseCase;
    private final GetCourseByIdUseCaseImpl getCourseByIdUseCase;
    private final GetFilteredCoursesUseCaseImpl getFilteredCoursesUseCase;
    private final UpdateCourseSemesterEndDateUseCaseImpl updateCourseSemesterEndDateUseCase;

    @Override
    public Course createCourse(Course course) {
        return createCourseUseCase.createCourse(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return getAllCoursesUseCase.getAllCourses();
    }

    @Override
    public Course getCourseById(UUID courseId) {
        return getCourseByIdUseCase.getCourseById(courseId);
    }

    @Override
    public Course updateCourseSemesterEndDate(UUID courseId, LocalDate semesterEndDate) {
        return updateCourseSemesterEndDateUseCase.updateCourseSemesterEndDate(courseId, semesterEndDate);
    }

    @Override
    public List<Course> getFilteredCourses(String courseName, String professorName, String consultationDay, Integer totalAppointments, Integer acceptedAppointments, Integer availableAppointments) {
        return getFilteredCoursesUseCase.getFilteredCourses(courseName, professorName, consultationDay, totalAppointments, acceptedAppointments, availableAppointments);
    }

    @Override
    public Course addProfessorToCourse(UUID courseId, UUID professorId) {
        return addProfessorToCourseUseCase.addProfessorToCourse(courseId, professorId);
    }
}
