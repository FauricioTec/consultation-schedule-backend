package qa.project.consultation_scheduler.appointment.domain.repository;

import org.springframework.data.jpa.domain.Specification;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;

public class AppointmentSpecification {

    public static Specification<Appointment> hasStudentIdCard(String studentIdCard) {
        return (root, query, criteriaBuilder) -> {
            if (studentIdCard == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("student").get("idCard"), studentIdCard);
        };
    }

    public static Specification<Appointment> hasCourseName(String courseName) {
        return (root, query, criteriaBuilder) -> {
            if (courseName == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("course").get("courseName"), courseName);
        };
    }

    public static Specification<Appointment> hasProfessorName(String professorName) {
        return (root, query, criteriaBuilder) -> {
            if (professorName == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("professor").get("name"), professorName);
        };
    }

    public static Specification<Appointment> hasDateTime(java.time.LocalDateTime dateTime) {
        return (root, query, criteriaBuilder) -> {
            if (dateTime == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("start"), dateTime);
        };
    }
}
