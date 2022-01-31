package ulaval.glo2003.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AgeValidator.class})
public @interface MinimumAge {
    String message() default "Invalid birthdate.";

    int age();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
