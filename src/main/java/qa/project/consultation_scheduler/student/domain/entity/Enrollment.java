package qa.project.consultation_scheduler.student.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import qa.project.consultation_scheduler._shared.BaseEntity;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.student.validation.annotation.ValidAttemptCount;
import qa.project.consultation_scheduler.student.validation.annotation.ValidStarRating;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "enrollment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "course_id"})
})
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
public class Enrollment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ValidStarRating
    @Column(nullable = false)
    private int starRating;

    @ValidAttemptCount
    @Column(nullable = false)
    private int attemptCount;

    public Enrollment(Course course, Student student, int attemptCount) {
        course.addEnrollment(this);
        this.course = course;
        this.student = student;
        this.attemptCount = attemptCount;
        this.starRating = 3;
    }
}
