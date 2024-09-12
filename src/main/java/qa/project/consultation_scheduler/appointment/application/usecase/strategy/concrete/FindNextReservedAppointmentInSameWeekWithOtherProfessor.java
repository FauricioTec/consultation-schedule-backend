package qa.project.consultation_scheduler.appointment.application.usecase.strategy.concrete;

import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentStrategy;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class FindNextReservedAppointmentInSameWeekWithOtherProfessor extends FindAppointmentStrategy {

    private final int minAvailabilityRate;

    public FindNextReservedAppointmentInSameWeekWithOtherProfessor(Student student, Course course, Professor professor, LocalDateTime from, int minAvailabilityRate) {
        super(student, course, professor, from);
        this.minAvailabilityRate = minAvailabilityRate;
    }

    public FindNextReservedAppointmentInSameWeekWithOtherProfessor(Student student, Course course, Professor professor, LocalDateTime from) {
        this(student, course, professor, from, 0);
    }

    @Override
    public Optional<Appointment> execute() {
        if (course.getAppointmentAvailabilityRate() < minAvailabilityRate) {
            return Optional.empty();
        }

        List<Professor> professors = course.getProfessors().stream()
                .filter(p -> !p.equals(professor))
                .filter(p -> p.getAvailableAppointmentsCount(from, course.getSemester().getEndDate().atTime(LocalTime.MAX)) > 0)
                .toList();

        for (Professor professor : professors) {
            FindNextReservedAppointmentInSameWeek findNextReservedAppointmentInSameWeek =
                    new FindNextReservedAppointmentInSameWeek(student, course, professor, from);
            Optional<Appointment> appointment = findNextReservedAppointmentInSameWeek.execute();
            if (appointment.isPresent()) {
                return appointment;
            }
        }
        return Optional.empty();
    }
}
