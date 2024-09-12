package qa.project.consultation_scheduler.appointment.application.usecase.strategy.concrete;

import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentStrategy;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.factory.AppointmentFactory;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
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

    private final List<Schedule> consultationSchedules;
    private final LocalDateTime to;

    public FindNextUnreservedAppointment(Student student, Course course, Professor professor, LocalDateTime from) {
        super(student, course, professor, from);
        this.consultationSchedules = professor.getSchedules().stream().sorted().toList();
        this.to = course.getSemester().getEndDate().atTime(LocalTime.MAX);
    }

    public FindNextUnreservedAppointment(Student student, Course course, Professor professor, LocalDateTime from, LocalDateTime to) {
        super(student, course, professor, from);
        this.consultationSchedules = professor.getSchedules().stream().sorted().toList();
        this.to = to.isAfter(course.getSemester().getEndDate().atTime(LocalTime.MAX)) ?
                course.getSemester().getEndDate().atTime(LocalTime.MAX) : to;
    }

    public FindNextUnreservedAppointment(Student student, Course course, Professor professor,
                                         List<Schedule> consultationSchedules,
                                         LocalDateTime from,
                                         LocalDateTime to) {
        super(student, course, professor, from);
        this.consultationSchedules = consultationSchedules.stream().sorted().toList();
        this.to = to.isAfter(course.getSemester().getEndDate().atTime(LocalTime.MAX)) ?
                course.getSemester().getEndDate().atTime(LocalTime.MAX) : to;
    }

    public FindNextUnreservedAppointment(Student student, Course course, Professor professor, List<Schedule> consultationSchedules, LocalDateTime from) {
        this(student, course, professor, consultationSchedules, from, course.getSemester().getEndDate().atTime(LocalTime.MAX));
    }

    @Override
    public Optional<Appointment> execute() {
        if (consultationSchedules.isEmpty()) {
            return Optional.empty();
        }

        LocalDateTime currentDateTime = adjustStartDateTime(from);

        while (currentDateTime.isBefore(to)) {
            Optional<Appointment> appointment = findAvailableSlotInSchedules(currentDateTime);

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

    private Optional<Appointment> findAvailableSlotInSchedules(LocalDateTime currentDateTime) {
        for (Schedule schedule : consultationSchedules) {
            if (schedule.isSameDay(currentDateTime) && (schedule.startsAfter(currentDateTime) || schedule.contains(currentDateTime))) {
                Optional<Appointment> appointment = findAppointmentSlotInSchedule(schedule, currentDateTime);
                if (appointment.isPresent()) {
                    return appointment;
                }
            }
        }
        return Optional.empty();
    }

    private Optional<Appointment> findAppointmentSlotInSchedule(Schedule
                                                                        schedule, LocalDateTime from) {
        LocalDateTime currentDateTime = from;
        while (currentDateTime.isBefore(LocalDateTime.of(currentDateTime.toLocalDate(), schedule.getEndTime()))) {
            Optional<Appointment> appointment = generateAppointmentSlot(schedule, currentDateTime);
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

    private Optional<Appointment> generateAppointmentSlot(Schedule schedule, LocalDateTime currentDateTime) {
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