package qa.project.consultation_scheduler.appointment.domain.repository;

import org.springframework.data.jpa.domain.Specification;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment_;
import qa.project.consultation_scheduler.course.domain.entity.Course_;
import qa.project.consultation_scheduler.professor.domain.entity.Professor_;
import qa.project.consultation_scheduler.student.domain.entity.Student_;

public class AppointmentSpecification {

    public static Specification<Appointment> hasStudentIdCard(String studentIdCard) {
        return (root, query, cb) -> {
            if (studentIdCard == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(Appointment_.student).get(Student_.idCard), studentIdCard);
        };
    }

    public static Specification<Appointment> hasCourseName(String courseName) {
        return (root, query, cb) -> {
            if (courseName == null) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get(Appointment_.course).get(Course_.name)), "%" + courseName.toLowerCase() + "%");
        };
    }

    public static Specification<Appointment> hasProfessorName(String professorName) {
        return (root, query, cb) -> {
            if (professorName == null) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get(Appointment_.professor).get(Professor_.name)), "%" + professorName.toLowerCase() + "%");
        };
    }

    public static Specification<Appointment> hasDateTime(java.time.LocalDateTime dateTime) {
        return (root, query, cb) -> {
            if (dateTime == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get(Appointment_.start), dateTime);
        };
    }
}