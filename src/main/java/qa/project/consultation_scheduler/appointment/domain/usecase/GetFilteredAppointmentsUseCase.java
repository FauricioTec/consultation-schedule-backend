package qa.project.consultation_scheduler.appointment.domain.usecase;

import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface GetFilteredAppointmentsUseCase {

    List<Appointment> getFilteredAppointments(String studentIdCard,
                                              String courseName,
                                              String professorName,
                                              LocalDateTime dateTime);

}
