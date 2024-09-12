package qa.project.consultation_scheduler.appointment.application.usecase.strategy.concrete;

import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentStrategy;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.entity.Status;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Optional;

public class FindNextReservedAppointmentInSameWeek extends FindAppointmentStrategy {

    private final int minAvailabilityRate;

    public FindNextReservedAppointmentInSameWeek(Student student, Course course, Professor professor, LocalDateTime from, int minAvailabilityRate) {
        super(student, course, professor, from);
        this.minAvailabilityRate = minAvailabilityRate;
    }

    public FindNextReservedAppointmentInSameWeek(Student student, Course course, Professor professor, LocalDateTime from) {
        this(student, course, professor, from, 0);
    }

    @Override
    public Optional<Appointment> execute() {
        if (course.getAppointmentAvailabilityRate() < minAvailabilityRate) {
            return Optional.empty();
        }

        List<Appointment> reservedAppointments = course.getAppointments().stream()
                .filter(appointment -> appointment.getStatus() == Status.ACCEPTED)
                .toList();

        Optional<Appointment> appointment = reservedAppointments.stream()
                .filter(reservedAppointment -> reservedAppointment.getStart().isAfter(from))
                .filter(reservedAppointment -> isSameWeek(from, reservedAppointment.getStart()))
                .filter(reservedAppointment -> reservedAppointment.getProfessor().equals(professor))
                .filter(reservedAppointment -> !reservedAppointment.getStudent().equals(student))
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
