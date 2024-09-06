package qa.project.consultation_scheduler.professor.validation.validator;

import jakarta.validation.ConstraintValidator;
import qa.project.consultation_scheduler.professor.validation.annotation.ValidAvailableSlots;

public class AvailableSlotsValidator implements ConstraintValidator<ValidAvailableSlots, Integer> {

    @Override
    public boolean isValid(Integer availableSlots, jakarta.validation.ConstraintValidatorContext constraintValidatorContext) {
        return availableSlots > 0;
    }
}
