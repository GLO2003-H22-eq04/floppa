package ulaval.glo2003.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class PriceValidator implements ConstraintValidator<MinimumPrice, Double> {

    private int minimumPrice;

    @Override
    public void initialize(MinimumPrice constraintAnnotation) {
        minimumPrice = constraintAnnotation.price();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        return value >= minimumPrice;
    }
}