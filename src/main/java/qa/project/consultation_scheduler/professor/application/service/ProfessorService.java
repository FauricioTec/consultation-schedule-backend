package qa.project.consultation_scheduler.professor.application.service;

import qa.project.consultation_scheduler.professor.domain.usecase.AddScheduleToProfessorUseCase;
import qa.project.consultation_scheduler.professor.domain.usecase.CreateProfessorUseCase;
import qa.project.consultation_scheduler.professor.domain.usecase.GetAllProfessorsUseCase;
import qa.project.consultation_scheduler.professor.domain.usecase.GetProfessorByIdUseCase;

public interface ProfessorService extends
        AddScheduleToProfessorUseCase,
        CreateProfessorUseCase,
        GetAllProfessorsUseCase,
        GetProfessorByIdUseCase {
}
