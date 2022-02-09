package ulaval.glo2003.Validation.Handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import ulaval.glo2003.Validation.Errors.ValidationError;
import ulaval.glo2003.Validation.ValidationExceptionResponseBuilder;

public class ValidationExceptionHandler implements ExceptionMapper<ValidationError> {
    private ValidationExceptionResponseBuilder responseBuilder = new ValidationExceptionResponseBuilder();

    @Override
    public Response toResponse(ValidationError e) {
        return responseBuilder.buildFromException(e);
    }
}