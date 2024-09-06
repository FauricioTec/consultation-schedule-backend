package qa.project.consultation_scheduler.professor.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import qa.project.consultation_scheduler.professor.validation.annotation.ValidScheduleDay;

import java.time.DayOfWeek;

public class ScheduleDayValidator implements ConstraintValidator<ValidScheduleDay, DayOfWeek> {

    @Override
    public boolean isValid(DayOfWeek dayOfWeek, ConstraintValidatorContext constraintValidatorContext) {
        if (dayOfWeek == null) {
            return false;
        }
        return switch (dayOfWeek) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> true;
            default -> false;
        };
    }
}
