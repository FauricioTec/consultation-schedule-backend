package qa.project.consultation_scheduler.course.validation.validator;

import jakarta.validation.ConstraintValidator;
import qa.project.consultation_scheduler.course.domain.entity.Semester;
import qa.project.consultation_scheduler.course.validation.annotation.ValidDateRange;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Semester> {
    @Override
    public boolean isValid(Semester semester, jakarta.validation.ConstraintValidatorContext constraintValidatorContext) {
        return semester.getStartDate().isBefore(semester.getEndDate());
    }
}
