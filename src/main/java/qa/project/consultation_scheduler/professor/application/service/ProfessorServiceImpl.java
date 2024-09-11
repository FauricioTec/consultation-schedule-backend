package qa.project.consultation_scheduler.professor.application.service;

import lombok.Builder;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;
import qa.project.consultation_scheduler.professor.domain.usecase.AddScheduleToProfessorUseCase;
import qa.project.consultation_scheduler.professor.domain.usecase.CreateProfessorUseCase;
import qa.project.consultation_scheduler.professor.domain.usecase.GetAllProfessorsUseCase;
import qa.project.consultation_scheduler.professor.domain.usecase.GetProfessorByIdUseCase;

import java.util.List;
import java.util.UUID;

@Builder
public class ProfessorServiceImpl implements ProfessorService {

    private final AddScheduleToProfessorUseCase addScheduleToProfessorUseCase;
    private final CreateProfessorUseCase createProfessorUseCase;
    private final GetAllProfessorsUseCase getAllProfessorsUseCase;
    private final GetProfessorByIdUseCase getProfessorByIdUseCase;

    @Override
    public Schedule addConsultationSchedule(UUID professorId, Schedule schedule) {
        return addScheduleToProfessorUseCase.addConsultationSchedule(professorId, schedule);
    }

    @Override
    public Professor createProfessor(Professor professor) {
        return createProfessorUseCase.createProfessor(professor);
    }

    @Override
    public List<Professor> getAllProfessors() {
        return getAllProfessorsUseCase.getAllProfessors();
    }

    @Override
    public Professor getProfessorById(UUID professorId) {
        return getProfessorByIdUseCase.getProfessorById(professorId);
    }
}
