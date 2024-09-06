package qa.project.consultation_scheduler.student.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import qa.project.consultation_scheduler.student.validation.validator.StarRatingValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StarRatingValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStarRating {
    String message() default "Star rating must be between 1 and 3";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
