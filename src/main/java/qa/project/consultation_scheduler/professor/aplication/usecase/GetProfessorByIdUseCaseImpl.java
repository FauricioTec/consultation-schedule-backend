package qa.project.consultation_scheduler.professor.aplication.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.repository.ProfessorRepository;
import qa.project.consultation_scheduler.professor.domain.usecase.GetProfessorByIdUseCase;

import java.util.UUID;

@Component
@AllArgsConstructor
public class GetProfessorByIdUseCaseImpl implements GetProfessorByIdUseCase {

    private final ProfessorRepository professorRepository;

    @Override
    public Professor getProfessorById(UUID professorId) {
        return professorRepository.findById(professorId).orElseThrow(
                () -> new RuntimeException("Professor not found with ID: " + professorId)
        );
    }
}
