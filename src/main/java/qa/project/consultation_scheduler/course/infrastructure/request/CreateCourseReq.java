package qa.project.consultation_scheduler.course.infrastructure.request;

import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.factory.CourseFactory;

public record CreateCourseReq(CreateSemesterReq semester, String name, String courseName) {

    public Course toEntity() {
        return CourseFactory.create(semester.toEntity(), name, courseName);
    }

}
