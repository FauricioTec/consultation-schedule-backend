package qa.project.consultation_scheduler.student.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.student.application.service.StudentService;
import qa.project.consultation_scheduler.student.application.service.StudentServiceImpl;
import qa.project.consultation_scheduler.student.application.usecase.*;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;

@Configuration
public class StudentServiceConfig {

    @Bean
    public StudentService studentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        return StudentServiceImpl.builder()
                .createStudentUseCase(new CreateStudentUseCaseImpl(studentRepository))
                .getAllStudentsUseCase(new GetAllStudentsUseCaseImpl(studentRepository))
                .getStudentByIdUseCase(new GetStudentByIdUseCaseImpl(studentRepository))
                .enrollStudentUseCase(new EnrollStudentUseCaseImpl(studentRepository, courseRepository))
                .getFilteredStudentsUseCase(new GetFilteredStudentsUseCaseImpl(studentRepository))
                .getAllEnrollmentsUseCase(new GetAllEnrollmentsUseCaseImpl(studentRepository))
                .getEnrollmentByIdUseCase(new GetEnrollmentByIdUseCaseImpl(studentRepository))
                .updateStarRatingUseCase(new UpdateStarRatingUseCaseImpl(studentRepository))
                .build();
    }
}
