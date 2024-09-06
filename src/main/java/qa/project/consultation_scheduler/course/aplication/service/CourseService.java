package qa.project.consultation_scheduler.course.aplication.service;

import qa.project.consultation_scheduler.course.domain.usecase.*;

public interface CourseService extends AddProfessorToCourseUseCase,
        CreateCourseUseCase,
        GetAllCoursesUseCase,
        GetCourseByIdUseCase,
        GetFilteredCoursesUseCase,
        UpdateCourseSemesterEndDateUseCase {
}
