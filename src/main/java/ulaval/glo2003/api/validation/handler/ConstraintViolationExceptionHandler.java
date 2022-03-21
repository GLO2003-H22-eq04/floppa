package ulaval.glo2003.api.validation.handler;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Payload;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ulaval.glo2003.api.validation.errors.InvalidParameterError;
import ulaval.glo2003.api.validation.errors.ValidationError;
import ulaval.glo2003.api.validation.ValidationExceptionResponseBuilder;

import java.util.Optional;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    private static final ValidationError DEFAULT_VALIDATION_ERROR = new InvalidParameterError("Valeur invalide.");

    private final ValidationExceptionResponseBuilder responseBuilder = new ValidationExceptionResponseBuilder();

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(ConstraintViolationException e) {
        return createResponse(e);
    }

    private Response createResponse(ConstraintViolationException e) {
        for (var error : e.getConstraintViolations())
            for (var errorType : error.getConstraintDescriptor().getPayload())
                if (ValidationError.class.isAssignableFrom(errorType))
                    return responseBuilder.buildFromException(getErrorInstance(errorType, error.getMessage()).orElse(DEFAULT_VALIDATION_ERROR));

        return responseBuilder.buildFromException(DEFAULT_VALIDATION_ERROR);
    }

    private Optional<ValidationError> getErrorInstance(Class<? extends Payload> errorType, String message) {
        try {
            return Optional.of((ValidationError) errorType.getDeclaredConstructor().newInstance(message));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}



