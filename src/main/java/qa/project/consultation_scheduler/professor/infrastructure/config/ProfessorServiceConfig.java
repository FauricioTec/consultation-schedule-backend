package qa.project.consultation_scheduler.professor.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qa.project.consultation_scheduler.professor.application.service.ProfessorService;
import qa.project.consultation_scheduler.professor.application.service.ProfessorServiceImpl;
import qa.project.consultation_scheduler.professor.application.usecase.AddScheduleToProfessorUseCaseImpl;
import qa.project.consultation_scheduler.professor.application.usecase.CreateProfessorUseCaseImpl;
import qa.project.consultation_scheduler.professor.application.usecase.GetAllProfessorsUseCaseImpl;
import qa.project.consultation_scheduler.professor.application.usecase.GetProfessorByIdUseCaseImpl;
import qa.project.consultation_scheduler.professor.domain.repository.ProfessorRepository;

@Configuration
public class ProfessorServiceConfig {

    @Bean
    public ProfessorService professorService(ProfessorRepository professorRepository) {
        return ProfessorServiceImpl.builder()
                .addScheduleToProfessorUseCase(new AddScheduleToProfessorUseCaseImpl(professorRepository))
                .createProfessorUseCase(new CreateProfessorUseCaseImpl(professorRepository))
                .getAllProfessorsUseCase(new GetAllProfessorsUseCaseImpl(professorRepository))
                .getProfessorByIdUseCase(new GetProfessorByIdUseCaseImpl(professorRepository))
                .build();
    }
}
