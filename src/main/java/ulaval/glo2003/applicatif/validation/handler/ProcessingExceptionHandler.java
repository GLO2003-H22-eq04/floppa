package ulaval.glo2003.applicatif.validation.handler;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ulaval.glo2003.applicatif.validation.errors.InvalidParameterError;
import ulaval.glo2003.applicatif.validation.errors.ValidationError;
import ulaval.glo2003.applicatif.validation.ValidationExceptionResponseBuilder;

@Provider
public class ProcessingExceptionHandler implements ExceptionMapper<ProcessingException> {

    private static final ValidationError DEFAULT_VALIDATION_ERROR = new InvalidParameterError("JSON invalide.");

    private final ValidationExceptionResponseBuilder responseBuilder = new ValidationExceptionResponseBuilder();

    @Override
    public Response toResponse(ProcessingException e) {
        return responseBuilder.buildFromException(DEFAULT_VALIDATION_ERROR);
    }
}