package qa.project.consultation_scheduler.appointment.application.usecase.strategy;

import lombok.AllArgsConstructor;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class FindAppointmentContext {
    private final List<FindAppointmentStrategy> strategies;

    public Optional<Appointment> findAppointment(LocalDateTime from) {
        for (FindAppointmentStrategy strategy : strategies) {
            Optional<Appointment> appointment = strategy.execute(from);
            if (appointment.isPresent()) {
                return appointment;
            }
        }
        return Optional.empty();
    }
}
