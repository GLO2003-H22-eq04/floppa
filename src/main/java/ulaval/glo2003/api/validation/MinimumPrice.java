package ulaval.glo2003.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PriceValidator.class})
public @interface MinimumPrice {
    String message() default "Invalid price";

    double price();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
