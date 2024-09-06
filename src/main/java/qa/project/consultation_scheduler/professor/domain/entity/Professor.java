package qa.project.consultation_scheduler.professor.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import qa.project.consultation_scheduler._shared.BaseEntity;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.course.domain.entity.Course;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "professor")
@Data
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Professor extends BaseEntity {

    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

    @ManyToMany(mappedBy = "professors")
    @JsonBackReference
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "professor")
    private List<Schedule> schedules = new ArrayList<>();

    @Column(nullable = false)
    @NotBlank
    private String name;

    public Professor(String name) {
        this.name = name;
    }

    public void addSchedule(Schedule schedule) {
        if (schedules.stream().anyMatch(schedule::overlaps)) {
            throw new IllegalArgumentException("Schedule overlaps with another schedule");
        }
        schedule.setProfessor(this);
        schedules.add(schedule);
    }

    public boolean hasAvailableAppointmentThisWeek() {
        LocalDateTime start = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end = start.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).with(LocalTime.MAX);
        return getAvailableAppointmentsCount(start, end) > 0;
    }

    @JsonIgnore
    public int getAvailableAppointmentsCount(LocalDateTime start, LocalDateTime end) {
        int count = 0;
        List<Schedule> sortedSchedules = schedules.stream().sorted().toList();
        start = adjustStartDate(start);
        LocalDateTime currentDateTime = start;
        while (currentDateTime.isBefore(end)) {
            count += sortedSchedules.stream().mapToInt(schedule -> schedule.getPossibleSlotsForDateTime(currentDateTime)).sum();
            start = getNextDay(start);
        }
        LocalDateTime temp = start;
        count -= (int) appointments.stream().filter(
                appointment -> appointment.getStart().isAfter(temp) && appointment.getStart().isBefore(end)).count();
        return count;
    }

    private LocalDateTime adjustStartDate(LocalDateTime start) {
        if (start.getDayOfWeek() == DayOfWeek.SATURDAY || start.getDayOfWeek() == DayOfWeek.SUNDAY) {
            start = start.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.MIN);
        }
        return start;
    }

    private LocalDateTime getNextDay(LocalDateTime date) {
        return date.getDayOfWeek() == DayOfWeek.FRIDAY ? date.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.MIN)
                : date.with(TemporalAdjusters.next(date.getDayOfWeek())).with(LocalTime.MIN);
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course); // Ensure the professor is added to the course as well.
        }
    }
}