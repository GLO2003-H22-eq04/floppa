package ulaval.glo2003.api.validation.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.glassfish.jersey.server.ParamException;
import ulaval.glo2003.api.validation.ValidationExceptionResponseBuilder;
import ulaval.glo2003.api.validation.errors.InvalidParameterError;

public class ParamExceptionHandler implements ExceptionMapper<ParamException> {

    private final ValidationExceptionResponseBuilder responseBuilder = new ValidationExceptionResponseBuilder();

    @Override
    public Response toResponse(ParamException e) {
        return responseBuilder.buildFromException(new InvalidParameterError(e.getParameterName() + "invalide"));
    }
}
