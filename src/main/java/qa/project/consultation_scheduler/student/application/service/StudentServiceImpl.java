package qa.project.consultation_scheduler.student.application.service;

import lombok.Builder;
import qa.project.consultation_scheduler.student.application.usecase.*;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment;
import qa.project.consultation_scheduler.student.domain.entity.Student;

import java.util.List;
import java.util.UUID;

@Builder
public class StudentServiceImpl implements StudentService {

    private final CreateStudentUseCaseImpl createStudentUseCase;
    private final GetAllStudentsUseCaseImpl getAllStudentsUseCase;
    private final GetStudentByIdUseCaseImpl getStudentByIdUseCase;
    private final EnrollStudentUseCaseImpl enrollStudentUseCase;
    private final GetFilteredStudentsUseCaseImpl getFilteredStudentsUseCase;
    private final GetAllEnrollmentsUseCaseImpl getAllEnrollmentsUseCase;
    private final GetEnrollmentByIdUseCaseImpl getEnrollmentByIdUseCase;
    private final UpdateStarRatingUseCaseImpl updateStarRatingUseCase;

    @Override
    public Student createStudent(Student student) {
        return createStudentUseCase.createStudent(student);
    }

    @Override
    public Enrollment enrollStudent(UUID studentId, UUID courseId, int attemptCount) {
        return enrollStudentUseCase.enrollStudent(studentId, courseId, attemptCount);
    }

    @Override
    public List<Enrollment> getAllStudentEnrollments(UUID studentId) {
        return getAllEnrollmentsUseCase.getAllStudentEnrollments(studentId);
    }

    @Override
    public List<Student> getAllStudents() {
        return getAllStudentsUseCase.getAllStudents();
    }

    @Override
    public Enrollment getStudentEnrollment(UUID studentId, UUID enrollmentId) {
        return getEnrollmentByIdUseCase.getStudentEnrollment(studentId, enrollmentId);
    }

    @Override
    public List<Student> getFilteredStudents(String campus, String idCard, String courseName, Integer attemptCount, Integer starRating) {
        return getFilteredStudentsUseCase.getFilteredStudents(campus, idCard, courseName, attemptCount, starRating);
    }

    @Override
    public Student getStudentById(UUID id) {
        return getStudentByIdUseCase.getStudentById(id);
    }

    @Override
    public Enrollment updateStarRating(UUID studentId, UUID enrollmentId, int starRating) {
        return updateStarRatingUseCase.updateStarRating(studentId, enrollmentId, starRating);
    }
}
