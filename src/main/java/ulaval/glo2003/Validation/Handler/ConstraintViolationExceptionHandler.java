package ulaval.glo2003.Validation.Handler;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Payload;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ulaval.glo2003.Validation.ValidationError;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    private static final String DEFAULT_ERROR_CODE = "INVALID_PARAM";

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(ConstraintViolationException e) {
        ErrorDTO responseContent = createErrorDTO(e);
        return Response.status(Response.Status.BAD_REQUEST).entity(responseContent).build();
    }

    private ErrorDTO createErrorDTO(ConstraintViolationException e) {
        for (var error : e.getConstraintViolations())
            for (var payload : error.getConstraintDescriptor().getPayload())
                if (ValidationError.class.isAssignableFrom(payload))
                    return new ErrorDTO(getCode(payload), error.getMessage());

        return new ErrorDTO(DEFAULT_ERROR_CODE, "Paramètre invalide");
    }

    private String getCode(Class<? extends Payload> payload) {
        try {
            var instance = (ValidationError) payload.getDeclaredConstructor().newInstance();
            return instance.getCode();
        } catch (Exception e) {
            return DEFAULT_ERROR_CODE;
        }
    }
}

class ErrorDTO {
    public String code;
    public String description;

    public ErrorDTO(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

