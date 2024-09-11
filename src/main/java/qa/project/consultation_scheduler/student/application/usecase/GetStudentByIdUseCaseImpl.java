package qa.project.consultation_scheduler.student.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;
import qa.project.consultation_scheduler.student.domain.usecase.GetStudentByIdUseCase;

import java.util.UUID;

@Component
@AllArgsConstructor
public class GetStudentByIdUseCaseImpl implements GetStudentByIdUseCase {

    private final StudentRepository studentRepository;

    @Override
    public Student getStudentById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
    }
}
