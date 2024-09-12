package qa.project.consultation_scheduler.appointment.domain.usecase;

import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GetNextAppointmentUseCase {

    Appointment getNextAppointment(UUID studentId, UUID courseId, UUID professorId, LocalDateTime from);
}
