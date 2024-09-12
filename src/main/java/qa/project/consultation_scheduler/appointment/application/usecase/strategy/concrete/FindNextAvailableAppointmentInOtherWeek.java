package qa.project.consultation_scheduler.appointment.application.usecase.strategy.concrete;

import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentStrategy;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

public class FindNextAvailableAppointmentInOtherWeek extends FindAppointmentStrategy {

    public FindNextAvailableAppointmentInOtherWeek(Student student, Course course, Professor professor, LocalDateTime from) {
        super(student, course, professor, from);
    }

    @Override
    public Optional<Appointment> execute() {
        LocalDateTime nextWeekStart = from.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.MIN);
        FindNextUnreservedAppointment findNextUnreservedAppointment = new FindNextUnreservedAppointment(student, course, professor, nextWeekStart);
        return findNextUnreservedAppointment.execute();
    }
}
