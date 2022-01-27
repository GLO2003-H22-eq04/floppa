package ulaval.glo2003;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MinimumAgeValidator.class})
public @interface MinimumAge {
    String message() default "Invalid birthdate.";

    int age() default 18;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
