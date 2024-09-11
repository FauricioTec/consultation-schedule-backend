package qa.project.consultation_scheduler.appointment.aplication.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.entity.Status;
import qa.project.consultation_scheduler.appointment.domain.factory.AppointmentFactory;
import qa.project.consultation_scheduler.appointment.domain.repository.AppointmentRepository;
import qa.project.consultation_scheduler.appointment.domain.usecase.GetNextAppointmentUseCase;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GetNextAppointmentUseCaseImpl implements GetNextAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public Appointment getNextAppointment(UUID studentId, UUID courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        validateStudentAndCourse(student, course);
        LocalDateTime from = getFromDateTime(student, course);
        Appointment appointment = findNextAppointment(student, course, from).orElseThrow(() -> new RuntimeException("No available appointments"));
        try {
            return appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new RuntimeException("Appointment could not be retrieved");
        }
    }

    private LocalDateTime getFromDateTime(Student student, Course course) {
        Optional<Appointment> pendingAppointment = appointmentRepository.findByStatusAndStudentAndCourse(Status.PENDING, student, course);
        return pendingAppointment.filter(appointment -> appointment.getStart().isAfter(LocalDateTime.now()))
                .map(appointment -> {
                    appointmentRepository.delete(appointment);
                    return appointment.getStart();
                })
                .orElseGet(() -> {
                    pendingAppointment.ifPresent(appointmentRepository::delete);
                    return LocalDateTime.now();
                });
    }

    //TODO: Aqui deberia implementarse la logica relacionada a la prioridad de los estudiantes
    private Optional<Appointment> findNextAppointment(Student student, Course course, LocalDateTime from) {

        //TODO: El profesor que atiendo deberia cambiar segun los veces que ha repetido el estudiante el curso, y la tasa de disponibilidad
        List<Schedule> consultationSchedules = course.getProfessors().stream()
                .filter(professor -> professor.getAvailableAppointmentsCount(from, course.getSemester().getEndDate().atTime(LocalTime.MAX)) > 0)
                .flatMap(professor -> professor.getSchedules().stream())
                .sorted()
                .toList(); //Obtener primer profesor con horario disponible

        LocalDateTime currentDateTime = from;
        LocalDateTime endOfSemester = course.getSemester().getEndDate().atTime(LocalTime.MAX);

        if (consultationSchedules.isEmpty()) {
            return Optional.empty();
        }

        if (currentDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || currentDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            currentDateTime = currentDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.MIN);
        }

        while (currentDateTime.isBefore(endOfSemester)) {
            Optional<Appointment> appointment = findAvailableSlotInSchedules(consultationSchedules, currentDateTime, student, course);

            if (appointment.isPresent()) {
                return appointment;
            }

            if (currentDateTime.getDayOfWeek() == DayOfWeek.FRIDAY) {
                currentDateTime = currentDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.MIN);
            } else {
                currentDateTime = currentDateTime.plusDays(1).with(LocalTime.MIN);
            }
        }

        return Optional.empty();
    }

    private Optional<Appointment> findAvailableSlotInSchedules(List<Schedule> schedules, LocalDateTime currentDateTime, Student student, Course course) {
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

    private Optional<Appointment> findAppointmentSlotInSchedule(Student student, Course course, Schedule schedule, LocalDateTime from) {
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

    public Optional<Appointment> generateAppointmentSlot(Student student, Course course, Schedule schedule, LocalDateTime currentDateTime) {
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

    public boolean validateOverlapping(List<Appointment> appointments, Appointment appointment) {
        return appointments.stream().anyMatch(appointment::overlaps);
    }

    private void validateStudentAndCourse(Student student, Course course) {
        if (!student.isEnrolledInCourse(course)) {
            throw new RuntimeException("Student is not enrolled in course with ID:" + course.getId());
        }
        if (course.getAvailableAppointments() == 0) {
            throw new RuntimeException("No available appointments");
        }
    }
}