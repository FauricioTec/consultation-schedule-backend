package qa.project.consultation_scheduler.professor.domain.usecase;

import qa.project.consultation_scheduler.professor.domain.entity.Professor;

import java.util.List;

public interface GetAllProfessorsUseCase {
    List<Professor> getAllProfessors();
}
