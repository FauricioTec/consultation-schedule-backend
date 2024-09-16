package qa.project.consultation_scheduler.appointment.domain.factory;

import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.time.Duration;
import java.time.LocalDateTime;

public class AppointmentFactory {

    public static Appointment create(Professor professor, Student student, Course course, LocalDateTime start, Duration duration) {
        return new Appointment(professor, student, course, start, duration);
    }
}
