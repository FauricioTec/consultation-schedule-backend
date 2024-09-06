package qa.project.consultation_scheduler.student.aplication.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;
import qa.project.consultation_scheduler.student.domain.usecase.UpdateStarRatingUseCase;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateStarRatingUseCaseImpl implements UpdateStarRatingUseCase {

    private final StudentRepository studentRepository;

    @Override
    public Enrollment updateStarRating(UUID studentId, UUID enrollmentId, int starRating) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        student.updateStarRating(starRating, enrollmentId);
        return studentRepository.save(student).getEnrollment(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + enrollmentId));
    }
}
