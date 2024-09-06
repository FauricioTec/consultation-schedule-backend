package qa.project.consultation_scheduler.course.infrastructure.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddProfessorToCourseReq(@NotNull(message = "Professor ID is required") UUID professorId) {
}
