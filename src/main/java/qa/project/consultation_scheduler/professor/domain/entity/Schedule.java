package qa.project.consultation_scheduler.professor.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import qa.project.consultation_scheduler._shared.BaseEntity;
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
@AllArgsConstructor
@NoArgsConstructor
@ValidTimeRange
public class Schedule extends BaseEntity implements Comparable<Schedule> {

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    @JsonBackReference
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Day of week is required")
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @Column(nullable = false)
    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @ValidAvailableSlots
    @Column(nullable = false)
    private int availableSlots;

    @ValidScheduleDay
    @Column(nullable = false)
    private int totalSlots;

    public boolean startsAfter(LocalDateTime dateTime) {
        if (isSameDay(dateTime)) {
            return dateTime.toLocalTime().isBefore(startTime);
        }
        return dateTime.getDayOfWeek().getValue() < dayOfWeek.getValue();
    }

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
        return dateTime.getDayOfWeek().equals(dayOfWeek)
                && !dateTime.toLocalTime().isBefore(startTime)
                && dateTime.toLocalTime().isBefore(endTime);
    }

    public boolean overlaps(Schedule schedule) {
        return this.startTime.isBefore(schedule.endTime) && this.endTime.isAfter(schedule.startTime);
    }

    public int getPossibleSlotsForDateTime(LocalDateTime dateTime) {
        if (isSameDay(dateTime)) {
            if (isAfter(dateTime)) {
                return availableSlots;
            }
            if (contains(dateTime)) {
                int eachAppointmentDuration = getDuration() / getAvailableSlots();
                int durationUntilEnd = (int) Duration.between(dateTime, getEndTime()).toMinutes();
                return durationUntilEnd / eachAppointmentDuration;
            }
        }
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
