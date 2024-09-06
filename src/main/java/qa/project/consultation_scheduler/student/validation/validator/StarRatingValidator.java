package qa.project.consultation_scheduler.student.validation.validator;

import jakarta.validation.ConstraintValidator;
import qa.project.consultation_scheduler.student.validation.annotation.ValidStarRating;

public class StarRatingValidator implements ConstraintValidator<ValidStarRating, Integer> {

    @Override
    public boolean isValid(Integer starRating, jakarta.validation.ConstraintValidatorContext constraintValidatorContext) {
        return starRating != null && starRating >= 1 && starRating <= 3;
    }

}
