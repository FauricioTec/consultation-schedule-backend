package qa.project.consultation_scheduler.student.infrastructure.request;

import qa.project.consultation_scheduler.student.validation.annotation.ValidStarRating;

public record UpdateStudentStarRatingReq(@ValidStarRating int starRating) {
}
