package qa.project.consultation_scheduler.appointment.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qa.project.consultation_scheduler.appointment.application.service.AppointmentService;
import qa.project.consultation_scheduler.appointment.application.service.AppointmentServiceImpl;
import qa.project.consultation_scheduler.appointment.application.usecase.AcceptAppointmentUseCaseImpl;
import qa.project.consultation_scheduler.appointment.application.usecase.GetFilteredAppointmentsUseCaseImpl;
import qa.project.consultation_scheduler.appointment.application.usecase.GetNextAppointmentUseCaseImpl;
import qa.project.consultation_scheduler.appointment.domain.repository.AppointmentRepository;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;

@Configuration
public class AppointmentServiceConfig {

    @Bean
    public AppointmentService appointmentService(AppointmentRepository appointmentRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        return AppointmentServiceImpl.builder()
                .acceptAppointmentUseCase(new AcceptAppointmentUseCaseImpl(appointmentRepository))
                .getFilteredAppointmentsUseCase(new GetFilteredAppointmentsUseCaseImpl(appointmentRepository))
                .getNextAppointmentUseCase(new GetNextAppointmentUseCaseImpl(appointmentRepository, studentRepository, courseRepository))
                .build();
    }
}