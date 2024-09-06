package qa.project.consultation_scheduler.course.domain.factory;

import qa.project.consultation_scheduler.course.domain.entity.Semester;

import java.time.LocalDate;

public class SemesterFactory {

    public static Semester create(LocalDate startDate, LocalDate endDate) {
        return new Semester(startDate, endDate);
    }
}
