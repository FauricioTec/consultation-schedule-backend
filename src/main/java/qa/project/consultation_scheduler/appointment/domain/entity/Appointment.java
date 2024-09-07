package qa.project.consultation_scheduler.appointment.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import qa.project.consultation_scheduler._shared.BaseEntity;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.time.Duration;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "appointment")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class Appointment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    @NotNull(message = "Professor is required")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @NotNull(message = "Student is required")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @NotNull(message = "Course is required")
    private Course course;

    @Column(nullable = false)
    @NotNull(message = "Start is required")
    private LocalDateTime start;

    @Column(nullable = false)
    @NotNull(message = "Duration is required")
    private Duration duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status is required")
    private Status status;

    public Appointment(Professor professor, Student student, Course course, LocalDateTime start, Duration duration) {
        this.professor = professor;
        this.student = student;
        this.course = course;
        this.start = start;
        this.duration = duration;
        this.status = Status.PENDING;
    }

    public void accept() {
        this.status = Status.ACCEPTED;
    }

    public boolean overlaps(Appointment other) {
        LocalDateTime thisEnd = this.start.plus(this.duration);
        LocalDateTime otherEnd = other.start.plus(other.duration);
        return this.start.isBefore(otherEnd) && thisEnd.isAfter(other.start);
    }
}
