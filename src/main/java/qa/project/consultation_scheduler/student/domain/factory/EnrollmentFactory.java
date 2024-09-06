package qa.project.consultation_scheduler.student.domain.factory;

import qa.project.consultation_scheduler.course.domain.entity.Course;
import qa.project.consultation_scheduler.student.domain.entity.Enrollment;
import qa.project.consultation_scheduler.student.domain.entity.Student;

public class EnrollmentFactory {

    public static Enrollment create(Student student, Course course, int attemptCount) {
        return new Enrollment(student, course, attemptCount);
    }
}
