package qa.project.consultation_scheduler.student.domain.repository;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment;
import qa.project.consultation_scheduler.student.domain.entity.Student;


public class StudentSpecification {

    public static Specification<Student> hasCampus(String campus) {
        return (root, query, criteriaBuilder) ->
                campus == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("campus"), campus);
    }

    public static Specification<Student> hasIdCard(String idCard) {
        return (root, query, criteriaBuilder) ->
                idCard == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("idCard"), idCard);
    }

    public static Specification<Student> hasCourseName(String courseName) {
        return (root, query, criteriaBuilder) -> {
            if (courseName == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Student, Enrollment> enrollments = root.join("enrollments");
            Join<Enrollment, Course> courses = enrollments.join("course");
            return criteriaBuilder.equal(courses.get("courseName"), courseName);
        };
    }

    public static Specification<Student> hasAttemptCount(Integer attemptCount) {
        return (root, query, criteriaBuilder) -> {
            if (attemptCount == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Student, Enrollment> enrollments = root.join("enrollments");
            return criteriaBuilder.equal(enrollments.get("attemptCount"), attemptCount);
        };
    }

    public static Specification<Student> hasStarRating(Integer starRating) {
        return (root, query, criteriaBuilder) -> {
            if (starRating == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Student, Enrollment> enrollments = root.join("enrollments");
            return criteriaBuilder.equal(enrollments.get("starRating"), starRating);
        };
    }
}