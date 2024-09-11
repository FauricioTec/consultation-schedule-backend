package qa.project.consultation_scheduler.appointment.application.usecase.strategy;

import lombok.AllArgsConstructor;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public abstract class FindAppointmentStrategy {

    protected final Student student;
    protected final Course course;

    public abstract Optional<Appointment> execute(LocalDateTime from);
}
