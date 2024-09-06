package qa.project.consultation_scheduler.student.domain.usecase;

import qa.project.consultation_scheduler.student.domain.entity.Student;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface GetStudentByIdUseCase {
   Student getStudentById(UUID id);
}
