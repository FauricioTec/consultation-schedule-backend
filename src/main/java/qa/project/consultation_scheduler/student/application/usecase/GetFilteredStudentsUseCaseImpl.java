package qa.project.consultation_scheduler.student.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.repository.StudentRepository;
import qa.project.consultation_scheduler.student.domain.repository.StudentSpecification;
import qa.project.consultation_scheduler.student.domain.usecase.GetFilteredStudentsUseCase;

import java.util.List;

@Component
@AllArgsConstructor
public class GetFilteredStudentsUseCaseImpl implements GetFilteredStudentsUseCase {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> getFilteredStudents(String campus, String idCard, String courseName, Integer attemptCount, Integer starRating) {
        return studentRepository.findAll(
                Specification.where(StudentSpecification.hasCampus(campus))
                        .and(StudentSpecification.hasIdCard(idCard))
                        .and(StudentSpecification.hasCourseName(courseName))
                        .and(StudentSpecification.hasAttemptCount(attemptCount))
                        .and(StudentSpecification.hasStarRating(starRating))
        );
    }
}
