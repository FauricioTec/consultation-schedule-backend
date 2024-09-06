package qa.project.consultation_scheduler.student.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import qa.project.consultation_scheduler.student.validation.validator.AttemptCountValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AttemptCountValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAttemptCount {
    String message() default "Attempt count must be greater than 1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
