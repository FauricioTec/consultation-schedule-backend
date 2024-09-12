package qa.project.consultation_scheduler.appointment.application.usecase;

import lombok.AllArgsConstructor;
import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentContext;
import qa.project.consultation_scheduler.appointment.application.usecase.strategy.FindAppointmentStrategy;
import qa.project.consultation_scheduler.appointment.application.usecase.strategy.concrete.*;
import qa.project.consultation_scheduler.appointment.domain.entity.Appointment;
import qa.project.consultation_scheduler.appointment.domain.entity.Status;
import qa.project.consultation_scheduler.appointment.domain.repository.AppointmentRepository;
import qa.project.consultation_scheduler.appointment.domain.usecase.GetNextAppointmentUseCase;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.*;


@AllArgsConstructor
public class GetNextAppointmentUseCaseImpl implements GetNextAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public Appointment getNextAppointment(UUID studentId, UUID courseId, UUID professorId, LocalDateTime from) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Professor professor = course.getProfessorById(professorId).orElseThrow(() -> new RuntimeException("Professor not found"));

        validateEnrollmentAndAvailability(student, course);

        appointmentRepository.deleteAll(appointmentRepository.findAllByStatusAndStudentAndCourse(Status.PENDING, student, course));

        LocalDateTime startDateTime = from == null ? LocalDateTime.now() : from;

        Appointment appointment = findAppointmentByPriority(student, course, professor, startDateTime)
                .orElseThrow(() -> new RuntimeException("No available appointments"));

        try {
            return appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new RuntimeException("Appointment could not be retrieved");
        }
    }

    private Optional<Appointment> findAppointmentByPriority(Student student, Course course, Professor professor, LocalDateTime from) {
        Map<Integer, Map<Integer, List<FindAppointmentStrategy>>> priorityMap = new HashMap<>();

        priorityMap.put(0, Map.of(
                1, List.of(new FindNextUnreservedAppointment(student, course, professor, from)),
                2, List.of(new FindNextUnreservedAppointmentInSameWeek(student, course, professor, from),
                        new FindNextReservedAppointmentInSameWeek(student, course, professor, from, 70),
                        new FindNextAvailableAppointmentInOtherWeek(student, course, professor, from)),
                3, List.of(new FindNextUnreservedAppointmentInSameWeek(student, course, professor, from),
                        new FindNextReservedAppointmentInSameWeek(student, course, professor, from, 50),
                        new FindNextAvailableAppointmentInOtherWeek(student, course, professor, from))));

        priorityMap.put(1, Map.of(
                1, List.of(new FindNextUnreservedAppointment(student, course, professor, from)),
                2, List.of(new FindNextReservedAppointmentInSameWeek(student, course, professor, from, 50),
                        new FindNextUnreservedAppointmentInSameWeek(student, course, professor, from),
                        new FindNextUnreservedAppointmentInSameWeekWithOtherProfessor(student, course, professor, from, 70),
                        new FindNextAvailableAppointmentInOtherWeek(student, course, professor, from)),
                3, List.of(new FindNextReservedAppointmentInSameWeek(student, course, professor, from),
                        new FindNextUnreservedAppointmentInSameWeek(student, course, professor, from),
                        new FindNextUnreservedAppointmentInSameWeekWithOtherProfessor(student, course, professor, from),
                        new FindNextReservedAppointmentInNextWeek(student, course, professor, from),
                        new FindNextAvailableAppointmentInOtherWeek(student, course, professor, from))));

        priorityMap.put(2, Map.of(1, List.of(
                        new FindNextUnreservedAppointment(student, course, professor, from)),
                2, List.of(new FindNextReservedAppointmentInSameWeek(student, course, professor, from, 35),
                        new FindNextUnreservedAppointmentInSameWeek(student, course, professor, from),
                        new FindNextReservedAppointmentInSameWeekWithOtherProfessor(student, course, professor, from, 60),
                        new FindNextUnreservedAppointmentInSameWeekWithOtherProfessor(student, course, professor, from, 70),
                        new FindNextUnreservedAppointment(student, course, professor, from)),
                3, List.of(new FindNextReservedAppointmentInSameWeek(student, course, professor, from),
                        new FindNextUnreservedAppointmentInSameWeek(student, course, professor, from),
                        new FindNextReservedAppointmentInSameWeekWithOtherProfessor(student, course, professor, from),
                        new FindNextUnreservedAppointmentInSameWeekWithOtherProfessor(student, course, professor, from),
                        new FindNextReservedAppointmentInNextWeek(student, course, professor, from),
                        new FindNextAvailableAppointmentInOtherWeek(student, course, professor, from))));

        int attemptCount = student.getAttemptCountForCourse(course).orElseThrow(()
                -> new RuntimeException("Student has not attempted course with ID:" + course.getId()));
        int starRating = student.getStarRatingForCourse(course).orElseThrow(()
                -> new RuntimeException("Student has not rated course with ID:" + course.getId()));

        List<FindAppointmentStrategy> priorityStrategies = priorityMap.getOrDefault(attemptCount, Map.of()).getOrDefault(starRating, List.of());

        FindAppointmentContext context = new FindAppointmentContext(priorityStrategies);
        return context.findAppointment();
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