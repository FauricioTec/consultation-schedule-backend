package qa.project.consultation_scheduler.appointment.application.usecase.strategy.concrete;

import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentStrategy;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class FindNextUnreservedAppointmentInSameWeekWithOtherProfessor extends FindAppointmentStrategy {

    private final int minAvailabilityRate;

    public FindNextUnreservedAppointmentInSameWeekWithOtherProfessor(Student student, Course course, Professor professor, LocalDateTime from, int minAvailabilityRate) {
        super(student, course, professor, from);
        this.minAvailabilityRate = minAvailabilityRate;
    }

    public FindNextUnreservedAppointmentInSameWeekWithOtherProfessor(Student student, Course course, Professor professor, LocalDateTime from) {
        this(student, course, professor, from, 0);
    }

    @Override
    public Optional<Appointment> execute() {
        if (course.getAppointmentAvailabilityRate() < minAvailabilityRate) {
            return Optional.empty();
        }

        List<Schedule> schedules = course.getProfessors().stream()
                .filter(p -> !p.equals(professor))
                .flatMap(p -> p.getSchedules().stream())
                .toList();

        FindNextUnreservedAppointment findNextUnreservedAppointment = new FindNextUnreservedAppointment(student, course, professor, schedules, from);

        return findNextUnreservedAppointment.execute();
    }
}
