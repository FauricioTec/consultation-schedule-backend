package qa.project.consultation_scheduler.student.domain.usecase;

import qa.project.consultation_scheduler.student.domain.entity.Student;

public interface CreateStudentUseCase {
    Student createStudent(Student student);
}
