package qa.project.consultation_scheduler.appointment.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import qa.project.consultation_scheduler.appointment.application.usecase.AcceptAppointmentUseCaseImpl;
import qa.project.consultation_scheduler.appointment.application.usecase.GetFilteredAppointmentsUseCaseImpl;
import qa.project.consultation_scheduler.appointment.application.usecase.GetNextAppointmentUseCaseImpl;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AcceptAppointmentUseCaseImpl acceptAppointmentUseCase;
    private final GetFilteredAppointmentsUseCaseImpl getFilteredAppointmentsUseCase;
    private final GetNextAppointmentUseCaseImpl getNextAppointmentUseCase;

    @Override
    public Appointment acceptAppointment(UUID appointmentId) {
        return acceptAppointmentUseCase.acceptAppointment(appointmentId);
    }

    @Override
    public List<Appointment> getFilteredAppointments(String studentIdCard, String courseName, String professorName, LocalDateTime dateTime) {
        return getFilteredAppointmentsUseCase.getFilteredAppointments(studentIdCard, courseName, professorName, dateTime);
    }

    @Override
    public Appointment getNextAppointment(UUID studentId, UUID courseId) {
        return getNextAppointmentUseCase.getNextAppointment(studentId, courseId);
    }
}
