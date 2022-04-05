package ulaval.glo2003.applicatif.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<MinimumPrice, Double> {

    private double minimumPrice;

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