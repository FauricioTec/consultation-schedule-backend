package qa.project.consultation_scheduler.student.domain.usecase;

import qa.project.consultation_scheduler.student.domain.entity.Enrollment;

import java.util.List;
import java.util.UUID;

public interface GetAllEnrollmentsUseCase {
    List<Enrollment> getAllStudentEnrollments(UUID studentId);
}
