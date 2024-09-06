package qa.project.consultation_scheduler.course.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import qa.project.consultation_scheduler._shared.BaseEntity;
import qa.project.consultation_scheduler.course.validation.annotation.ValidDateRange;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "semester")
@Data
@NoArgsConstructor
public class Semester extends BaseEntity {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    public Semester(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}