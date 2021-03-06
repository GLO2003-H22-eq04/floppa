package ulaval.glo2003.applicatif.validation.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import ulaval.glo2003.applicatif.validation.errors.ValidationError;
import ulaval.glo2003.applicatif.validation.ValidationExceptionResponseBuilder;

public class ValidationExceptionHandler implements ExceptionMapper<ValidationError> {
    private final ValidationExceptionResponseBuilder responseBuilder = new ValidationExceptionResponseBuilder();

    @Override
    public Response toResponse(ValidationError e) {
        return responseBuilder.buildFromException(e);
    }
}
