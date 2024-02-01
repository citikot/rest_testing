package it.discovery.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = TitleValidator.class)
public @interface TitleConstraint {

    String message() default "Book title should start with capital letter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
