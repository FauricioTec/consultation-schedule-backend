package qa.project.consultation_scheduler.professor.application.usecase;

import lombok.AllArgsConstructor;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.repository.ProfessorRepository;
import qa.project.consultation_scheduler.professor.domain.usecase.CreateProfessorUseCase;

@AllArgsConstructor
public class CreateProfessorUseCaseImpl implements CreateProfessorUseCase {

    private final ProfessorRepository professorRepository;

    @Override
    public Professor createProfessor(Professor professor) {
        try {
            return professorRepository.save(professor);
        } catch (Exception e) {
            throw new RuntimeException("Error creating professor");
        }
    }
}
