package qa.project.consultation_scheduler.student.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;
import qa.project.consultation_scheduler.student.domain.usecase.EnrollStudentUseCase;

import java.util.UUID;

@Component
@AllArgsConstructor
public class EnrollStudentUseCaseImpl implements EnrollStudentUseCase {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public Enrollment enrollStudent(UUID studentId, UUID courseId, int attemptCount) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        try {
            student.enroll(course, attemptCount);
            studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enroll student in course with ID: " + courseId);
        }

        return student.getEnrollments().stream()
                .filter(e -> e.getCourse().getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to enroll student in course with ID: " + courseId));
    }
}