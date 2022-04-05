package ulaval.glo2003.applicatif.validation.errors;

import jakarta.validation.Payload;
import jakarta.ws.rs.core.Response;

public abstract class ValidationError extends Exception implements Payload {
    public ValidationError(String message) {
        super(message);
    }

    public abstract String getCode();

    public abstract Response.Status getStatus();
}

