package qa.project.consultation_scheduler.professor.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;

import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<Professor, UUID>, JpaSpecificationExecutor<Professor> {
}
