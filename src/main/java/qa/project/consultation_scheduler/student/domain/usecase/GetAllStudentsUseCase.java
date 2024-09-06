package qa.project.consultation_scheduler.student.domain.usecase;

import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.util.List;

public interface GetAllStudentsUseCase {
    List<Student> getAllStudents();
}
