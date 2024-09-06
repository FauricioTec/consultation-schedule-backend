package qa.project.consultation_scheduler.course.aplication.usecase;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.repository.CourseRepository;
import qa.project.consultation_scheduler.course.domain.usecase.AddProfessorToCourseUseCase;
import qa.project.consultation_scheduler.professor.domain.entity.Professor;
import qa.project.consultation_scheduler.professor.domain.repository.ProfessorRepository;

import java.util.UUID;
import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class AddProfessorToCourseUseCaseImpl implements AddProfessorToCourseUseCase {

    private static final Logger logger = Logger.getLogger(AddProfessorToCourseUseCaseImpl.class.getName());

    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    @Override
    public Course addProfessorToCourse(UUID courseId, UUID professorId) {
        logger.info("Starting addProfessorToCourse with courseId: " + courseId + " and professorId: " + professorId);
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new RuntimeException("Professor not found with ID: " + professorId));
        try {
            logger.info("Adding professor to course : " + course.toString());
            course.addProfessor(professor);
            Course updatedCourse = courseRepository.save(course);
            logger.info("Successfully added professor to course : " + updatedCourse.toString());
            return course;
        } catch (Exception e) {
            logger.severe("Error adding professor to course: " + e.getMessage());
            throw new RuntimeException("Error adding professor to course", e);
        }
    }
}
