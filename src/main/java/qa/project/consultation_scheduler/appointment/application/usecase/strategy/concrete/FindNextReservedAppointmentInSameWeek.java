package qa.project.consultation_scheduler.appointment.application.usecase.strategy.concrete;

import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentStrategy;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.entity.Status;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Optional;

public class FindNextReservedAppointmentInSameWeek extends FindAppointmentStrategy {

    public FindNextReservedAppointmentInSameWeek(Student student, Course course) {
        super(student, course);
    }

    @Override
    public Optional<Appointment> execute(LocalDateTime from) {
        List<Appointment> reservedAppointments = course.getAppointments().stream()
                .filter(appointment -> appointment.getStatus() == Status.ACCEPTED)
                .toList();

        Optional<Appointment> appointment = reservedAppointments.stream()
                .filter(reservedAppointment -> reservedAppointment.getStart().isAfter(from))
                .filter(reservedAppointment -> isSameWeek(from, reservedAppointment.getStart()))
                .findFirst();

        if (appointment.isPresent()) {
            appointment.get().reassign(student);
            return appointment;
        }
        return Optional.empty();
    }

    private boolean isSameWeek(LocalDateTime date1, LocalDateTime date2) {
        WeekFields weekFields = WeekFields.of(date1.getDayOfWeek(), 1);
        return date1.get(weekFields.weekOfYear()) == date2.get(weekFields.weekOfYear());
    }
}
