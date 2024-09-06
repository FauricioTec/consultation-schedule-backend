package qa.project.consultation_scheduler.course.domain.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;

public class CourseSpecification {
    public static Specification<Course> hasCourseName(String courseName) {
        return (root, query, criteriaBuilder) ->
                courseName == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("courseName"), courseName);
    }

    public static Specification<Course> hasProfessorName(String professorName) {
        return (root, query, criteriaBuilder) -> {
            if (professorName == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Course, Professor> professor = root.join("professors");
            return criteriaBuilder.equal(professor.get("name"), professorName);
        };
    }

    public static Specification<Course> hasConsultationDay(String consultationDay) {
        return (root, query, criteriaBuilder) -> {
            if (consultationDay == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Course, Professor> professor = root.join("professors");
            Join<Professor, Schedule> schedule = professor.join("schedules");
            return criteriaBuilder.equal(schedule.get("dayOfWeek"), consultationDay);
        };
    }
}
