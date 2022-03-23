package ulaval.glo2003.api.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MessageValidator.class})
public @interface MinimumCharacter {
    String message() default "Invalid Message.";

    int nbrCharater();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
