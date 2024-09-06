package qa.project.consultation_scheduler.student.domain.usecase;

import qa.project.consultation_scheduler.student.domain.entity.Enrollment;

import java.util.UUID;

public interface UpdateStarRatingUseCase {

    Enrollment updateStarRating(UUID studentId, UUID enrollmentId, int starRating);
}
