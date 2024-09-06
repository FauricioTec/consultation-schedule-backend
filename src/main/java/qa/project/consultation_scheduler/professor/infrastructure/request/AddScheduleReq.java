package qa.project.consultation_scheduler.professor.infrastructure.request;

import qa.project.consultation_scheduler.professor.domain.entity.Schedule;
import qa.project.consultation_scheduler.professor.domain.factory.ScheduleFactory;
import qa.project.consultation_scheduler.professor.validation.annotation.ValidAvailableSlots;
import qa.project.consultation_scheduler.professor.validation.annotation.ValidScheduleDay;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AddScheduleReq(@ValidScheduleDay DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, @ValidAvailableSlots int availableSlots) {
    public AddScheduleReq {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }

    public Schedule toEntity() {
        return ScheduleFactory.create(dayOfWeek, startTime, endTime, availableSlots);
    }
}
