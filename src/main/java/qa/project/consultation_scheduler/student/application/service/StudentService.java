package qa.project.consultation_scheduler.student.application.service;

import qa.project.consultation_scheduler.student.domain.usecase.*;

public interface StudentService extends
        CreateStudentUseCase,
        GetStudentByIdUseCase,
        GetAllStudentsUseCase,
        GetEnrollmentByIdUseCase,
        GetAllEnrollmentsUseCase,
        EnrollStudentUseCase,
        UpdateStarRatingUseCase,
        GetFilteredStudentsUseCase {
}
