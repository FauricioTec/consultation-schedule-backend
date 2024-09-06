package qa.project.consultation_scheduler.student.domain.usecase;

import qa.project.consultation_scheduler.student.domain.entity.Enrollment;

import java.util.UUID;

public interface EnrollStudentUseCase {
    Enrollment enrollStudent(UUID studentId, UUID courseId, int attemptCount);
}
