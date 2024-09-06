package qa.project.consultation_scheduler.course.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qa.project.consultation_scheduler.course.domain.entity.Semester;

import java.util.UUID;

public interface SemesterRepository extends JpaRepository<Semester, UUID> {
}
