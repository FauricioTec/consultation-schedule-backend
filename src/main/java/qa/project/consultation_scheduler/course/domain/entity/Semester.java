package qa.project.consultation_scheduler.course.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
public class Semester {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    public Semester(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}