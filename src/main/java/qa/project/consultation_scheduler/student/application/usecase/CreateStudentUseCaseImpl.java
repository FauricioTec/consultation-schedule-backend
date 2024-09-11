package qa.project.consultation_scheduler.student.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;
import qa.project.consultation_scheduler.student.domain.usecase.CreateStudentUseCase;

@Component
@AllArgsConstructor
public class CreateStudentUseCaseImpl implements CreateStudentUseCase {

    private final StudentRepository studentRepository;

    @Override
    public Student createStudent(Student student) {
        try {
            return studentRepository.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create student");
        }
    }
}
