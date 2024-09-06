package qa.project.consultation_scheduler.student.domain.factory;

import qa.project.consultation_scheduler.student.domain.entity.Student;

public class StudentFactory {

    public static Student create(String name, String idCard, String campus) {
        return new Student(name, idCard, campus);
    }
}
