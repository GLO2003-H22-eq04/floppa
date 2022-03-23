package ulaval.glo2003.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<RequirementPhoneNum, String> {
    @Override
    public void initialize(RequirementPhoneNum constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        Pattern phonePattern = Pattern.compile("^\\d{10,11}$");
        Matcher phoneMatcher = phonePattern.matcher(phoneNumber);
        return (phoneMatcher.matches());
    }
}
