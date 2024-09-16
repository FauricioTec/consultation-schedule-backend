package qa.project.consultation_scheduler.professor.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import qa.project.consultation_scheduler.professor.validation.validator.ScheduleDayValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ScheduleDayValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidScheduleDay {
    String message() default "Day of week must be between MONDAY and FRIDAY";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
