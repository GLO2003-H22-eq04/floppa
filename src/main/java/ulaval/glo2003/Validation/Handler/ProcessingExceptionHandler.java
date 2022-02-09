package ulaval.glo2003.Validation.Handler;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ulaval.glo2003.Validation.Errors.InvalidParameterError;
import ulaval.glo2003.Validation.Errors.ValidationError;
import ulaval.glo2003.Validation.ValidationExceptionResponseBuilder;

@Provider
public class ProcessingExceptionHandler implements ExceptionMapper<ProcessingException> {

    private static final ValidationError DEFAULT_VALIDATION_ERROR = new InvalidParameterError("JSON invalide.");

    private final ValidationExceptionResponseBuilder responseBuilder = new ValidationExceptionResponseBuilder();

    @Override
    public Response toResponse(ProcessingException e) {
        return responseBuilder.buildFromException(DEFAULT_VALIDATION_ERROR);
    }
}