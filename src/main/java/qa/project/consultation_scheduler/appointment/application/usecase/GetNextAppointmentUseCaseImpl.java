package qa.project.consultation_scheduler.appointment.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentContext;
import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentStrategy;
import qa.project.consultation_scheduler.appointment.application.usecase.strategy.concrete.*;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.entity.Status;
import qa.project.consultation_scheduler.appointment.domain.repository.AppointmentRepository;
import qa.project.consultation_scheduler.appointment.domain.usecase.GetNextAppointmentUseCase;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.*;

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

        validateEnrollmentAndAvailability(student, course);
        LocalDateTime from = determineStartDateTime(student, course);

        Appointment appointment = findAppointmentByPriority(student, course, from)
                .orElseThrow(() -> new RuntimeException("No available appointments"));

        try {
            return appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new RuntimeException("Appointment could not be retrieved");
        }
    }

    private LocalDateTime determineStartDateTime(Student student, Course course) {
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

    private Optional<Appointment> findAppointmentByPriority(Student student, Course course, LocalDateTime from) {
        Map<Integer, Map<Integer, List<FindAppointmentStrategy>>> priorityMap = new HashMap<>();

        priorityMap.put(1, Map.of(
                1, List.of(new FindNextUnreservedAppointment(student, course)),
                2, List.of(new FindNextUnreservedAppointmentInSameWeek(student, course),
                        new FindNextReservedAppointmentInSameWeek(student, course)),
                3, List.of(new FindNextUnreservedAppointmentInSameWeek(student, course),
                        new FindNextAvailableAppointmentInOtherWeek(student, course))
        ));

        priorityMap.put(2, Map.of(
                1, List.of(new FindNextUnreservedAppointment(student, course)),
                2, List.of(new FindNextReservedAppointmentInSameWeek(student, course),
                        new FindNextUnreservedAppointmentInSameWeek(student, course),
                        new FindNextAvailableAppointmentInOtherWeek(student, course)),
                3, List.of(new FindNextReservedAppointmentInSameWeek(student, course),
                        new FindNextUnreservedAppointmentInSameWeek(student, course),
                        new FindNextReservedAppointmentInNextWeek(student, course))
        ));

        priorityMap.put(3, Map.of(
                1, List.of(new FindNextUnreservedAppointment(student, course)),
                2, List.of(new FindNextReservedAppointmentInSameWeek(student, course),
                        new FindNextUnreservedAppointmentInSameWeek(student, course)),
                3, List.of(new FindNextReservedAppointmentInSameWeek(student, course),
                        new FindNextUnreservedAppointmentInSameWeek(student, course),
                        new FindNextReservedAppointmentInNextWeek(student, course))
        ));

        int attemptCount = student.getAttemptCountForCourse(course).orElseThrow(()
                -> new RuntimeException("Student has not attempted course with ID:" + course.getId()));
        int starRating = student.getStarRatingForCourse(course).orElseThrow(()
                -> new RuntimeException("Student has not rated course with ID:" + course.getId()));

        List<FindAppointmentStrategy> priorityStrategies = priorityMap.getOrDefault(attemptCount, priorityMap.get(3)).get(starRating);

        FindAppointmentContext context = new FindAppointmentContext(priorityStrategies);
        return context.findAppointment(from);
    }

    private void validateEnrollmentAndAvailability(Student student, Course course) {
        if (!student.isEnrolledInCourse(course)) {
            throw new RuntimeException("Student is not enrolled in course with ID:" + course.getId());
        }
        if (course.getAvailableAppointments() == 0) {
            throw new RuntimeException("No available appointments");
        }
    }
}