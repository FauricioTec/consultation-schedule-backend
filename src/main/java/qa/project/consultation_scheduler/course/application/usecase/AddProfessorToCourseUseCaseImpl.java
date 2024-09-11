package qa.project.consultation_scheduler.course.application.usecase;

import lombok.AllArgsConstructor;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.course.domain.usecase.AddProfessorToCourseUseCase;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.repository.ProfessorRepository;

import java.util.UUID;

@AllArgsConstructor
public class AddProfessorToCourseUseCaseImpl implements AddProfessorToCourseUseCase {

    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    @Override
    public Course addProfessorToCourse(UUID courseId, UUID professorId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new RuntimeException("Professor not found with ID: " + professorId));
        try {
            course.addProfessor(professor);
            return courseRepository.save(course);
        } catch (Exception e) {
            throw new RuntimeException("Error adding professor to course", e);
        }
    }
}
