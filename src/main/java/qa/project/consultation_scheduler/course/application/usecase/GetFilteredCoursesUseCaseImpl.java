package qa.project.consultation_scheduler.course.application.usecase;

import lombok.AllArgsConstructor;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.course.domain.repository.CourseSpecification;
import qa.project.consultation_scheduler.course.domain.usecase.GetFilteredCoursesUseCase;

import java.util.List;

@AllArgsConstructor
public class GetFilteredCoursesUseCaseImpl implements GetFilteredCoursesUseCase {

    private final CourseRepository courseRepository;

    @Override
    public List<Course> getFilteredCourses(String courseName,
                                           String professorName,
                                           String consultationDay,
                                           Integer totalAppointments,
                                           Integer acceptedAppointments,
                                           Integer availableAppointments) {
        List<Course> filteredCourses = courseRepository.findAll(
                CourseSpecification
                        .hasCourseName(courseName)
                        .and(CourseSpecification.hasProfessorName(professorName))
                        .and(CourseSpecification.hasConsultationDay(consultationDay))
        );

        System.out.println("filteredCourses = " + filteredCourses);

        if (totalAppointments != null) {
            filteredCourses.removeIf(course -> course.getTotalAppointments() != totalAppointments);
        }

        if (acceptedAppointments != null) {
            filteredCourses.removeIf(course -> course.getAcceptedAppointments() != acceptedAppointments);
        }

        if (availableAppointments != null) {
            filteredCourses.removeIf(course -> course.getAvailableAppointments() != availableAppointments);
        }

        return filteredCourses;
    }
}
