package qa.project.consultation_scheduler.student.aplication.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;
import qa.project.consultation_scheduler.student.domain.usecase.GetAllStudentsUseCase;

import java.util.List;

@Component
@AllArgsConstructor
public class GetAllStudentsUseCaseImpl implements GetAllStudentsUseCase {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
