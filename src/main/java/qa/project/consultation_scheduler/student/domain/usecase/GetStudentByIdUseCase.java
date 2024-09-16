package qa.project.consultation_scheduler.student.domain.usecase;

import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.util.UUID;

public interface GetStudentByIdUseCase {
   Student getStudentById(UUID id);
}
