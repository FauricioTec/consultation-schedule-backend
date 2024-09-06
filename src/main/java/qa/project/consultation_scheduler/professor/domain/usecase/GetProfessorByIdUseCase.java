package qa.project.consultation_scheduler.professor.domain.usecase;

import qa.project.consultation_scheduler.professor.domain.entity.Professor;

import java.util.UUID;

public interface GetProfessorByIdUseCase {

    Professor getProfessorById(UUID professorId);
}
