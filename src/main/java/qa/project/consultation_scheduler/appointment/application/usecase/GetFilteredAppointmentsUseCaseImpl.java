package qa.project.consultation_scheduler.appointment.application.usecase;

import lombok.AllArgsConstructor;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.repository.AppointmentRepository;
import qa.project.consultation_scheduler.appointment.domain.repository.AppointmentSpecification;
import qa.project.consultation_scheduler.appointment.domain.usecase.GetFilteredAppointmentsUseCase;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
public class GetFilteredAppointmentsUseCaseImpl implements GetFilteredAppointmentsUseCase {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getFilteredAppointments(String studentIdCard, String courseName, String professorName, LocalDateTime dateTime) {
        return appointmentRepository.findAll(
                AppointmentSpecification
                        .hasStudentIdCard(studentIdCard)
                        .and(AppointmentSpecification.hasCourseName(courseName))
                        .and(AppointmentSpecification.hasProfessorName(professorName))
                        .and(AppointmentSpecification.hasDateTime(dateTime))
        );
    }

}
