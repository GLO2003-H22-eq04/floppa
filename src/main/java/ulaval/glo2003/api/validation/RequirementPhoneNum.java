package ulaval.glo2003.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PhoneNumberValidator.class})
public @interface RequirementPhoneNum {
    String message() default "Invalid Phone-Number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
