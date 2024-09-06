package qa.project.consultation_scheduler.course.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import qa.project.consultation_scheduler.course.validation.validator.DateRangeValidator;
import qa.project.consultation_scheduler.professor.validation.validator.TimeRangeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateRangeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    String message() default "Start date must be before end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
