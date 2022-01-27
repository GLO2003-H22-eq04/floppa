package ulaval.glo2003;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {

    private int minimumAge;

    @Override
    public void initialize(MinimumAge constraintAnnotation) {
        minimumAge = constraintAnnotation.age();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        // Ajoute une journée pour pouvoir accepter l'age exact à la journée prêt.
        return birthDate.isBefore(LocalDate.now().minusYears(minimumAge).plusDays(1));
    }
}
