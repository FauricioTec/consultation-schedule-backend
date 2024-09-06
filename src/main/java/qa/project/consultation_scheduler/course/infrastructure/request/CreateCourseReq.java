package qa.project.consultation_scheduler.course.infrastructure.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.factory.CourseFactory;

public record CreateCourseReq(@NotNull CreateSemesterReq semester,
                              @NotBlank(message = "Course code is required") String courseCode,
                              @NotBlank(message = "Course name is required") String courseName) {

    public Course toEntity() {
        return CourseFactory.create(semester.toEntity(), courseCode, courseName);
    }

}
