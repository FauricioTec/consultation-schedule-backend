package qa.project.consultation_scheduler.student.infrastructure.request;

import jakarta.validation.constraints.NotNull;
import qa.project.consultation_scheduler.student.validation.annotation.ValidAttemptCount;

import java.util.UUID;

public record EnrollStudentReq(@NotNull UUID courseId, @ValidAttemptCount int attemptCount) {
}
