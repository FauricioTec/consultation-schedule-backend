package qa.project.consultation_scheduler.professor.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import qa.project.consultation_scheduler._common.BaseEntity;
import qa.project.consultation_scheduler.professor.validation.annotation.ValidAvailableSlots;
import qa.project.consultation_scheduler.professor.validation.annotation.ValidScheduleDay;
import qa.project.consultation_scheduler.professor.validation.annotation.ValidTimeRange;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "schedule", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "professor_id"})})
@Data
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@ValidTimeRange
public class Schedule extends BaseEntity implements Comparable<Schedule> {

    public Schedule(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, int availableSlots) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableSlots = availableSlots;
    }

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    @JsonBackReference
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ValidScheduleDay
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @ValidAvailableSlots
    @Column(nullable = false)
    private int availableSlots;

    public boolean startsAfter(LocalDateTime dateTime) {
        if (isSameDay(dateTime)) {
            return dateTime.toLocalTime().isBefore(startTime);
        }
        return dateTime.getDayOfWeek().getValue() < dayOfWeek.getValue();
    }

    @JsonIgnore
    public int getDuration() {
        return (int) startTime.until(endTime, java.time.temporal.ChronoUnit.MINUTES);
    }

    public boolean isSameDay(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().equals(dayOfWeek);
    }

    public boolean isAfter(LocalDateTime dateTime) {
        if (dateTime.getDayOfWeek().equals(dayOfWeek)) {
            return dateTime.toLocalTime().isBefore(startTime);
        }
        return dateTime.getDayOfWeek().getValue() < dayOfWeek.getValue();
    }

    public boolean contains(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().equals(dayOfWeek) && !dateTime.toLocalTime().isBefore(startTime) && dateTime.toLocalTime().isBefore(endTime);
    }

    public boolean overlaps(Schedule schedule) {
        if (!this.dayOfWeek.equals(schedule.dayOfWeek)) {
            return false;
        }
        return this.startTime.isBefore(schedule.endTime) && this.endTime.isAfter(schedule.startTime);
    }

    public int getPossibleSlotsForDateTime(LocalDateTime dateTime) {
        if (isSameDay(dateTime)) {
            LocalTime currentTime = dateTime.toLocalTime();
            if (currentTime.isBefore(startTime)) {
                return availableSlots;
            }
            if (contains(dateTime)) {
                // Calculate the number of remaining slots if the time is within the range
                int eachAppointmentDuration = getDuration() / getAvailableSlots();
                int minutesUntilEnd = (int) Duration.between(currentTime, endTime).toMinutes();
                return minutesUntilEnd / eachAppointmentDuration;
            }
        }
        // Return 0 if the date does not match the schedule's day or the time is outside the range
        return 0;
    }

    @Override
    public int compareTo(Schedule o) {
        if (this.dayOfWeek.equals(o.dayOfWeek)) {
            return this.startTime.compareTo(o.startTime);
        }
        return this.dayOfWeek.compareTo(o.dayOfWeek);
    }
}