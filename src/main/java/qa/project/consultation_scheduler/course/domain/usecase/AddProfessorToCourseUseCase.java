package qa.project.consultation_scheduler.course.domain.usecase;

import qa.project.consultation_scheduler.course.domain.entity.Course;

import java.util.UUID;

public interface AddProfessorToCourseUseCase {

    Course addProfessorToCourse(UUID courseId, UUID professorId);

}
