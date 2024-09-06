package qa.project.consultation_scheduler.professor.aplication.usecase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;
import qa.project.consultation_scheduler.professor.domain.repository.ProfessorRepository;
import qa.project.consultation_scheduler.professor.domain.usecase.AddScheduleToProfessorUseCase;

import java.util.UUID;

@Component
@AllArgsConstructor
public class AddScheduleToProfessorUseCaseImpl implements AddScheduleToProfessorUseCase {

    private final ProfessorRepository professorRepository;

    @Override
    public Schedule addConsultationSchedule(UUID professorId, Schedule schedule) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(
                () -> new RuntimeException("Professor not found with ID: " + professorId)
        );
        try {
            professor.addSchedule(schedule);
            professorRepository.save(professor);
            return schedule;
        } catch (Exception e) {
            throw new RuntimeException("Error adding schedule to professor");
        }
    }

}
