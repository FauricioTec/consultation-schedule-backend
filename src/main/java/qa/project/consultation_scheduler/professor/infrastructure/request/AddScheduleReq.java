package qa.project.consultation_scheduler.professor.infrastructure.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;
import qa.project.consultation_scheduler.professor.domain.factory.ScheduleFactory;
import qa.project.consultation_scheduler.professor.validation.annotation.ValidAvailableSlots;
import qa.project.consultation_scheduler.professor.validation.annotation.ValidScheduleDay;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AddScheduleReq(
        @ValidScheduleDay DayOfWeek dayOfWeek,
        @NotNull @JsonDeserialize(using = LocalTimeDeserializer.class) LocalTime startTime,
        @NotNull @JsonDeserialize(using = LocalTimeDeserializer.class) LocalTime endTime,
        @ValidAvailableSlots int availableSlots
) {
    @AssertTrue(message = "Start time must be before end time")
    public boolean isStartTimeBeforeEndTime() {
        return startTime != null && endTime != null && startTime.isBefore(endTime);
    }

    public Schedule toEntity() {
        return ScheduleFactory.create(dayOfWeek, startTime, endTime, availableSlots);
    }
}
