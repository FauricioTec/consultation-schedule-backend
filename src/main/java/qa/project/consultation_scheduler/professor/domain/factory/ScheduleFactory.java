package qa.project.consultation_scheduler.professor.domain.factory;

import qa.project.consultation_scheduler.professor.domain.entity.Schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ScheduleFactory {

    public static Schedule create(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, int availableSlots) {
        return new Schedule(dayOfWeek, startTime, endTime, availableSlots);
    }
}
