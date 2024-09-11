package qa.project.consultation_scheduler.course.application.service;

import lombok.Builder;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.usecase.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public class CourseServiceImpl implements CourseService {

    private final AddProfessorToCourseUseCase addProfessorToCourseUseCase;
    private final CreateCourseUseCase createCourseUseCase;
    private final GetAllCoursesUseCase getAllCoursesUseCase;
    private final GetCourseByIdUseCase getCourseByIdUseCase;
    private final GetFilteredCoursesUseCase getFilteredCoursesUseCase;
    private final UpdateCourseSemesterEndDateUseCase updateCourseSemesterEndDateUseCase;

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
