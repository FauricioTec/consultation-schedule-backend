package qa.project.consultation_scheduler.appointment.application.usecase.strategy.concrete;

import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentStrategy;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.entity.Status;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

public class FindNextReservedAppointmentInNextWeek extends FindAppointmentStrategy {

    public FindNextReservedAppointmentInNextWeek(Student student, Course course, Professor professor, LocalDateTime from) {
        super(student, course, professor, from);
    }

    @Override
    public Optional<Appointment> execute() {
        List<Appointment> reservedAppointments = course.getAppointments().stream()
                .filter(appointment -> appointment.getStatus() == Status.ACCEPTED)
                .toList();

        LocalDateTime nextMonday = from.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.MIN);
        LocalDateTime nextSunday = nextMonday.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).with(LocalTime.MAX);

        Optional<Appointment> appointment = reservedAppointments.stream()
                .filter(reservedAppointment -> reservedAppointment.getStart().isAfter(nextMonday))
                .filter(reservedAppointment -> reservedAppointment.getStart().isBefore(nextSunday))
                .filter(reservedAppointment -> reservedAppointment.getProfessor().equals(professor))
                .filter(reservedAppointment -> !reservedAppointment.getStudent().equals(student))
                .findFirst();

        if (appointment.isPresent()) {
            appointment.get().reassign(student);
            return appointment;
        }
        return Optional.empty();
    }
}
