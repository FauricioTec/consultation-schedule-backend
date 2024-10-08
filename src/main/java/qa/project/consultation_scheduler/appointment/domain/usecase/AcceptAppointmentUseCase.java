package qa.project.consultation_scheduler.appointment.domain.usecase;

import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;

import java.util.UUID;

public interface AcceptAppointmentUseCase {

    Appointment acceptAppointment(UUID appointmentId);

}
