package qa.project.consultation_scheduler.professor.application.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.repository.ProfessorRepository;
import qa.project.consultation_scheduler.professor.domain.usecase.GetAllProfessorsUseCase;

import java.util.List;

@Component
@AllArgsConstructor
public class GetAllProfessorsUseCaseImpl implements GetAllProfessorsUseCase {

    private final ProfessorRepository professorRepository;

    @Override
    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }
}
