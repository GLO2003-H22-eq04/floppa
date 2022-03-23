package ulaval.glo2003.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmailValidator.class})
public @interface RequirementEmail {
    String message() default "Invalid Email.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
