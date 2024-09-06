package qa.project.consultation_scheduler.course.domain.usecase;

import qa.project.consultation_scheduler.course.domain.entity.Course;

import java.util.List;

public interface GetFilteredCoursesUseCase {
    List<Course> getFilteredCourses(String courseName,
                                    String professorName,
                                    String consultationDay,
                                    Integer totalAppointments,
                                    Integer acceptedAppointments,
                                    Integer availableAppointments);
}
