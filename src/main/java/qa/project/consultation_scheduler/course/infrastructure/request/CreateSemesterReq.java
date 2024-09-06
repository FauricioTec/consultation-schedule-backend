package qa.project.consultation_scheduler.course.infrastructure.request;

import qa.project.consultation_scheduler.course.domain.entity.Semester;

import java.time.LocalDate;

public record CreateSemesterReq(LocalDate startDate, LocalDate endDate) {

    public Semester toEntity() {
        return new Semester(startDate, endDate);
    }
}
