package ulaval.glo2003.Validation;

import jakarta.validation.Payload;

public abstract class ValidationError implements Payload {
    public abstract String getCode();
}

