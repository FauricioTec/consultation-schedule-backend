package qa.project.consultation_scheduler.professor.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import qa.project.consultation_scheduler.professor.validation.validator.AvailableSlotsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AvailableSlotsValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAvailableSlots {
    String message() default "Available slots must be greater than 0";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
