package qa.project.consultation_scheduler.appointment.application.usecase;

import lombok.AllArgsConstructor;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.repository.AppointmentRepository;
import qa.project.consultation_scheduler.appointment.domain.usecase.AcceptAppointmentUseCase;

import java.util.UUID;


@AllArgsConstructor
public class AcceptAppointmentUseCaseImpl implements AcceptAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment acceptAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new RuntimeException("Appointment not found")
        );
        try {
            appointment.accept();
            return appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new RuntimeException("Appointment could not be accepted");
        }
    }
}
