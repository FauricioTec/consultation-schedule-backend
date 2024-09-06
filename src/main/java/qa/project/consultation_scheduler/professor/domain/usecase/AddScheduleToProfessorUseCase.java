package qa.project.consultation_scheduler.professor.domain.usecase;

import qa.project.consultation_scheduler.professor.domain.entity.Schedule;

import java.util.UUID;

public interface AddScheduleToProfessorUseCase {
    Schedule addConsultationSchedule(UUID professorId, Schedule schedule);
}
