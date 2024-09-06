package qa.project.consultation_scheduler.course.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import qa.project.consultation_scheduler._shared.BaseEntity;
import qa.project.consultation_scheduler.course.validation.annotation.ValidDateRange;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "semester")
@Data
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@NoArgsConstructor
@ValidDateRange
public class Semester extends BaseEntity {

    public Semester(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference
    private Course course;
}