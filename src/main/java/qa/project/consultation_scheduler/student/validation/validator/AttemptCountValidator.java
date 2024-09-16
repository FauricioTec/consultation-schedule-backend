package qa.project.consultation_scheduler.student.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import qa.project.consultation_scheduler.student.validation.annotation.ValidAttemptCount;

public class AttemptCountValidator implements ConstraintValidator<ValidAttemptCount, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value >= 0;
    }

}