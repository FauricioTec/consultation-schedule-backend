package qa.project.consultation_scheduler.appointment.application.service;

import qa.project.consultation_scheduler.appointment.domain.usecase.AcceptAppointmentUseCase;
import qa.project.consultation_scheduler.appointment.domain.usecase.GetFilteredAppointmentsUseCase;
import qa.project.consultation_scheduler.appointment.domain.usecase.GetNextAppointmentUseCase;

public interface AppointmentService extends
        AcceptAppointmentUseCase,
        GetFilteredAppointmentsUseCase,
        GetNextAppointmentUseCase {
}
