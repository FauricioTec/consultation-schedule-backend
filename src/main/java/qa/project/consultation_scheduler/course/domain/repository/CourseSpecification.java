package qa.project.consultation_scheduler.course.domain.repository;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.entity.Course_;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.entity.Professor_;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule_;

public class CourseSpecification {
    public static Specification<Course> hasCourseName(String courseName) {
        return (root, query, cb) -> courseName == null ? cb.conjunction() : cb.like(cb.lower(root.get(Course_.name)), "%" + courseName.toLowerCase() + "%");
    }

    public static Specification<Course> hasProfessorName(String professorName) {
        return (root, query, cb) -> {
            if (professorName == null) {
                return cb.conjunction();
            }
            Join<Course, Professor> professor = root.join("professors");
            return cb.like(cb.lower(professor.get(Professor_.name)), "%" + professorName.toLowerCase() + "%");
        };
    }

    public static Specification<Course> hasConsultationDay(String consultationDay) {
        return (root, query, cb) -> {
            if (consultationDay == null) {
                return cb.conjunction();
            }
            Join<Course, Professor> professor = root.join("professors");
            Join<Professor, Schedule> schedule = professor.join("schedules");
            return cb.equal(schedule.get(Schedule_.dayOfWeek), consultationDay.toUpperCase());
        };
    }
}
