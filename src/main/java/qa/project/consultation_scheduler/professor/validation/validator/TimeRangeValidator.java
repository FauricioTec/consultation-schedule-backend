package qa.project.consultation_scheduler.professor.validation.validator;

import jakarta.validation.ConstraintValidator;
import qa.project.consultation_scheduler.professor.domain.entity.Schedule;
import qa.project.consultation_scheduler.professor.validation.annotation.ValidTimeRange;

public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, Schedule> {

    @Override
    public boolean isValid(Schedule schedule, jakarta.validation.ConstraintValidatorContext constraintValidatorContext) {
        return schedule.getStartTime().isBefore(schedule.getEndTime());
    }
}
