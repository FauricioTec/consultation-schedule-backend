package qa.project.consultation_scheduler.appointment.application.service;

import lombok.Builder;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.usecase.AcceptAppointmentUseCase;
import qa.project.consultation_scheduler.appointment.domain.usecase.GetFilteredAppointmentsUseCase;
import qa.project.consultation_scheduler.appointment.domain.usecase.GetNextAppointmentUseCase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public class AppointmentServiceImpl implements AppointmentService {

    private final AcceptAppointmentUseCase acceptAppointmentUseCase;
    private final GetFilteredAppointmentsUseCase getFilteredAppointmentsUseCase;
    private final GetNextAppointmentUseCase getNextAppointmentUseCase;

    @Override
    public Appointment acceptAppointment(UUID appointmentId) {
        return acceptAppointmentUseCase.acceptAppointment(appointmentId);
    }

    @Override
    public List<Appointment> getFilteredAppointments(String studentIdCard, String courseName, String professorName, LocalDateTime dateTime) {
        return getFilteredAppointmentsUseCase.getFilteredAppointments(studentIdCard, courseName, professorName, dateTime);
    }

    @Override
    public Appointment getNextAppointment(UUID studentId, UUID courseId, LocalDateTime from) {
        return getNextAppointmentUseCase.getNextAppointment(studentId, courseId, from);
    }
}
