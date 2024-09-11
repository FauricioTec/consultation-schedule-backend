package qa.project.consultation_scheduler.student.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import qa.project.consultation_scheduler._common.BaseEntity;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.course.domain.entity.Course;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "student")
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class Student extends BaseEntity {

    @OneToMany
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Enrollment> enrollments = new ArrayList<>();

    @Column(unique = true, nullable = false)
    @NotNull
    private String idCard;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String campus;

    public Student(String name, String idCard, String campus) {
        this.name = name;
        this.idCard = idCard;
        this.campus = campus;
    }

    public void enroll(Course course, int attemptCount) {
        if (enrollments.stream().anyMatch(e -> e.getCourse().equals(course))) {
            throw new IllegalArgumentException("Student is already enrolled in this course");
        }
        Enrollment enrollment = new Enrollment(course, this, attemptCount); // Se pasa `this` para establecer la relación correctamente
        enrollments.add(enrollment);
    }

    public boolean isEnrolledInCourse(Course course) {
        return enrollments.stream().anyMatch(e -> e.getCourse().equals(course));
    }

    public void updateStarRating(int newStarRating, UUID enrollmentId) {
        Optional<Enrollment> enrollment = enrollments.stream()
                .filter(e -> e.getId().equals(enrollmentId))
                .findFirst();
        if (enrollment.isPresent()) {
            enrollment.get().setStarRating(newStarRating);
        } else {
            throw new IllegalArgumentException("Enrollment not found with ID: " + enrollmentId);
        }
    }

    public Optional<Enrollment> getEnrollment(UUID enrollmentId) {
        return enrollments.stream()
                .filter(e -> e.getId().equals(enrollmentId))
                .findFirst();
    }

    public OptionalInt getStarRatingForCourse(Course course) {
        return enrollments.stream()
                .filter(e -> e.getCourse().equals(course))
                .mapToInt(Enrollment::getStarRating)
                .findFirst();
    }

    public OptionalInt getAttemptCountForCourse(Course course) {
        return enrollments.stream()
                .filter(e -> e.getCourse().equals(course))
                .mapToInt(Enrollment::getAttemptCount)
                .findFirst();
    }
}
