package qa.project.consultation_scheduler.professor.aplication.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import qa.project.consultation_scheduler.professor.aplication.usecase.AddScheduleToProfessorUseCaseImpl;
import qa.project.consultation_scheduler.professor.aplication.usecase.CreateProfessorUseCaseImpl;
import qa.project.consultation_scheduler.professor.aplication.usecase.GetAllProfessorsUseCaseImpl;
import qa.project.consultation_scheduler.professor.aplication.usecase.GetProfessorByIdUseCaseImpl;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;
import qa.project.consultation_scheduler.professor.domain.usecase.AddScheduleToProfessorUseCase;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final AddScheduleToProfessorUseCaseImpl addScheduleToProfessorUseCase;
    private final CreateProfessorUseCaseImpl createProfessorUseCase;
    private final GetAllProfessorsUseCaseImpl getAllProfessorsUseCase;
    private final GetProfessorByIdUseCaseImpl getProfessorByIdUseCase;

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
