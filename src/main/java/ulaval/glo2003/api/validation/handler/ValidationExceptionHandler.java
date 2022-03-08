package ulaval.glo2003.api.validation.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import ulaval.glo2003.api.validation.errors.ValidationError;
import ulaval.glo2003.api.validation.ValidationExceptionResponseBuilder;

public class ValidationExceptionHandler implements ExceptionMapper<ValidationError> {
    private ValidationExceptionResponseBuilder responseBuilder = new ValidationExceptionResponseBuilder();

    @Override
    public Response toResponse(ValidationError e) {
        return responseBuilder.buildFromException(e);
    }
}
