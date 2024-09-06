package qa.project.consultation_scheduler.student.domain.repository;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.entity.Course_;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment_;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.entity.Student_;

public class StudentSpecification {

    public static Specification<Student> hasCampus(String campus) {
        return (root, query, cb) ->
                campus == null ? cb.conjunction() : cb.like(cb.lower(root.get(Student_.campus)), "%" + campus.toLowerCase() + "%");
    }

    public static Specification<Student> hasIdCard(String idCard) {
        return (root, query, criteriaBuilder) ->
                idCard == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get(Student_.idCard), idCard);
    }

    public static Specification<Student> hasCourseName(String courseName) {
        return (root, query, cb) -> {
            if (courseName == null) {
                return cb.conjunction();
            }
            Join<Student, Enrollment> enrollments = root.join(Student_.enrollments);
            Join<Enrollment, Course> courses = enrollments.join(Enrollment_.course);
            return cb.like(cb.lower(courses.get(Course_.name)), "%" + courseName.toLowerCase() + "%");
        };
    }

    public static Specification<Student> hasAttemptCount(Integer attemptCount) {
        return (root, query, cb) -> {
            if (attemptCount == null) {
                return cb.conjunction();
            }
            Join<Student, Enrollment> enrollments = root.join(Student_.enrollments);
            return cb.equal(enrollments.get(Enrollment_.attemptCount), attemptCount);
        };
    }

    public static Specification<Student> hasStarRating(Integer starRating) {
        return (root, query, cb) -> {
            if (starRating == null) {
                return cb.conjunction();
            }
            Join<Student, Enrollment> enrollments = root.join(Student_.enrollments);
            return cb.equal(enrollments.get(Enrollment_.starRating), starRating);
        };
    }
}