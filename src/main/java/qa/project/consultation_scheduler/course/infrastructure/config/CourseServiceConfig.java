package qa.project.consultation_scheduler.course.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qa.project.consultation_scheduler.course.application.service.CourseService;
import qa.project.consultation_scheduler.course.application.service.CourseServiceImpl;
import qa.project.consultation_scheduler.course.application.usecase.*;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.professor.domain.repository.ProfessorRepository;

@Configuration
public class CourseServiceConfig {

    @Bean
    public CourseService courseService(CourseRepository courseRepository, ProfessorRepository professorRepository) {
        return CourseServiceImpl.builder()
                .addProfessorToCourseUseCase(new AddProfessorToCourseUseCaseImpl(courseRepository, professorRepository))
                .createCourseUseCase(new CreateCourseUseCaseImpl(courseRepository))
                .getAllCoursesUseCase(new GetAllCoursesUseCaseImpl(courseRepository))
                .getCourseByIdUseCase(new GetCourseByIdUseCaseImpl(courseRepository))
                .getFilteredCoursesUseCase(new GetFilteredCoursesUseCaseImpl(courseRepository))
                .updateCourseSemesterEndDateUseCase(new UpdateCourseSemesterEndDateUseCaseImpl(courseRepository))
                .build();
    }
}