package qa.project.consultation_scheduler.student.aplication.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;
import qa.project.consultation_scheduler.student.domain.usecase.GetAllEnrollmentsUseCase;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GetAllEnrollmentsUseCaseImpl implements GetAllEnrollmentsUseCase {

    private final StudentRepository studentRepository;

    @Override
    public List<Enrollment> getAllStudentEnrollments(UUID studentId) {
        return studentRepository.findById(studentId)
                .map(Student::getEnrollments)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }

}
