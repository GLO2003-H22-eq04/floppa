package ulaval.glo2003.Validation;

import jakarta.validation.Payload;

public abstract class ValidationError extends Exception implements Payload {
    public ValidationError(String message) {
        super(message);
    }

    public abstract String getCode();
}

