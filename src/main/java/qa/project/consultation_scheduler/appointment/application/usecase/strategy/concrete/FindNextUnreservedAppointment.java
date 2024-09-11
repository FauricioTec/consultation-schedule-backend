package qa.project.consultation_scheduler.appointment.application.usecase.strategy.concrete;

import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentStrategy;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.factory.AppointmentFactory;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

public class FindNextUnreservedAppointment extends FindAppointmentStrategy {

    public FindNextUnreservedAppointment(Student student, Course course) {
        super(student, course);
    }

    @Override
    public Optional<Appointment> execute(LocalDateTime from) {
        List<Schedule> consultationSchedules = course.getProfessors().stream()
                .filter(professor -> professor.getAvailableAppointmentsCount(from, course.getSemester().getEndDate().atTime(LocalTime.MAX)) > 0)
                .flatMap(professor -> professor.getSchedules().stream())
                .sorted()
                .toList();

        if (consultationSchedules.isEmpty()) {
            return Optional.empty();
        }

        LocalDateTime currentDateTime = from;
        LocalDateTime endOfSemester = course.getSemester().getEndDate().atTime(LocalTime.MAX);

        currentDateTime = adjustStartDateTime(currentDateTime);

        while (currentDateTime.isBefore(endOfSemester)) {
            Optional<Appointment> appointment = findAvailableSlotInSchedules(consultationSchedules, currentDateTime, student, course);

            if (appointment.isPresent()) {
                return appointment;
            }

            currentDateTime = getNextDay(currentDateTime);
        }

        return Optional.empty();
    }

    private LocalDateTime adjustStartDateTime(LocalDateTime from) {
        return from.getDayOfWeek() == DayOfWeek.SATURDAY || from.getDayOfWeek() == DayOfWeek.SUNDAY ?
                from.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.MIN) : from;
    }

    private LocalDateTime getNextDay(LocalDateTime currentDateTime) {
        return currentDateTime.getDayOfWeek() == DayOfWeek.FRIDAY ?
                currentDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.MIN) :
                currentDateTime.plusDays(1).with(LocalTime.MIN);
    }

    private Optional<Appointment> findAvailableSlotInSchedules(List<Schedule> schedules, LocalDateTime
            currentDateTime, Student student, Course course) {
        for (Schedule schedule : schedules) {
            if (schedule.isSameDay(currentDateTime) && (schedule.startsAfter(currentDateTime) || schedule.contains(currentDateTime))) {
                Optional<Appointment> appointment = findAppointmentSlotInSchedule(student, course, schedule, currentDateTime);
                if (appointment.isPresent()) {
                    return appointment;
                }
            }
        }
        return Optional.empty();
    }

    private Optional<Appointment> findAppointmentSlotInSchedule(Student student, Course course, Schedule
            schedule, LocalDateTime from) {
        LocalDateTime currentDateTime = from;
        while (currentDateTime.isBefore(LocalDateTime.of(currentDateTime.toLocalDate(), schedule.getEndTime()))) {
            Optional<Appointment> appointment = generateAppointmentSlot(student, course, schedule, currentDateTime);
            if (appointment.isPresent()) {
                if (!validateOverlapping(schedule.getProfessor().getAppointments(), appointment.get())) {
                    return appointment;
                }
                currentDateTime = currentDateTime.plusMinutes(appointment.get().getDuration().toMinutes());
            } else {
                break;
            }
        }
        return Optional.empty();
    }

    private Optional<Appointment> generateAppointmentSlot(Student student, Course course, Schedule
            schedule, LocalDateTime currentDateTime) {
        int slotCount = schedule.getAvailableSlots();
        int slotDurationMinutes = schedule.getDuration() / slotCount;

        if (schedule.startsAfter(currentDateTime)) {
            LocalDateTime appointmentDateTime = currentDateTime
                    .withHour(schedule.getStartTime().getHour()).withMinute(schedule.getStartTime().getMinute());
            Duration appointmentDuration = Duration.ofMinutes(slotDurationMinutes);
            return Optional.of(
                    AppointmentFactory.create(schedule.getProfessor(), student, course, appointmentDateTime, appointmentDuration));
        }
        int durationUntilEnd = (int) Duration.between(currentDateTime, LocalDateTime.of(currentDateTime.toLocalDate(), schedule.getEndTime())).toMinutes();
        if (durationUntilEnd / slotDurationMinutes > 0) {
            int minutesUntilNextSlot = durationUntilEnd % slotDurationMinutes;
            LocalDateTime appointmentStartTime = currentDateTime.plusMinutes(minutesUntilNextSlot);
            Duration appointmentDuration = Duration.ofMinutes(slotDurationMinutes);
            return Optional.of(AppointmentFactory.create(schedule.getProfessor(), student, course, appointmentStartTime, appointmentDuration));
        }

        return Optional.empty();
    }

    private boolean validateOverlapping(List<Appointment> appointments, Appointment appointment) {
        return appointments.stream().anyMatch(appointment::overlaps);
    }
}