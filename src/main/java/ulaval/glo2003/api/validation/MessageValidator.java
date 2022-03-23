package ulaval.glo2003.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MessageValidator implements ConstraintValidator<MinimumCharacter, String> {

    private int nbrMinChar;

    @Override
    public void initialize(MinimumCharacter constraintAnnotation) {
        nbrMinChar = constraintAnnotation.nbrCharater();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String message, ConstraintValidatorContext constraintValidatorContext) {
        return (message.length() >= nbrMinChar);
    }
}
