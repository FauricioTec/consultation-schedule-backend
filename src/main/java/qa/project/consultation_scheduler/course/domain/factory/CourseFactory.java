package qa.project.consultation_scheduler.course.domain.factory;

import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.course.domain.entity.Semester;

public class CourseFactory {

    public static Course create(Semester semester, String name, String courseName) {
        return new Course(semester, name, courseName);
    }
}
