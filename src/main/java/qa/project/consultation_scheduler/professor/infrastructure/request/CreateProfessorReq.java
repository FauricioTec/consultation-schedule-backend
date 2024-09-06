package qa.project.consultation_scheduler.professor.infrastructure.request;

import jakarta.validation.constraints.NotBlank;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.factory.ProfessorFactory;

public record CreateProfessorReq(@NotBlank String name) {

    public Professor toEntity() {
        return ProfessorFactory.create(name);
    }
}
