package qa.project.consultation_scheduler.student.infrastructure.request;

import jakarta.validation.constraints.NotBlank;
import qa.project.consultation_scheduler.student.domain.entity.Student;
import qa.project.consultation_scheduler.student.domain.factory.StudentFactory;

public record CreateStudentReq(@NotBlank String idCard, @NotBlank String name, @NotBlank String campus) {
    public Student toEntity() {
        return StudentFactory.create(name, idCard, campus);
    }
}
