package qa.project.consultation_scheduler.student.application.usecase;

import lombok.AllArgsConstructor;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;
import qa.project.consultation_scheduler.student.domain.usecase.GetEnrollmentByIdUseCase;

import java.util.UUID;

@AllArgsConstructor
public class GetEnrollmentByIdUseCaseImpl implements GetEnrollmentByIdUseCase {

    private final StudentRepository studentRepository;

    @Override
    public Enrollment getStudentEnrollment(UUID studentId, UUID enrollmentId) {
        return studentRepository.findById(studentId)
                .map(Student::getEnrollments)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId))
                .stream()
                .filter(e -> e.getId().equals(enrollmentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + enrollmentId));
    }
}
