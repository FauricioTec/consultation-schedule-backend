package qa.project.consultation_scheduler.student.domain.usecase;

import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.util.List;

public interface GetFilteredStudentsUseCase {

    List<Student> getFilteredStudents(String campus, String idCard, String courseName,Integer attemptCount, Integer starRating);
}
