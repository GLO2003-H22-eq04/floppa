package ulaval.glo2003.applicatif.validation;

import jakarta.ws.rs.core.Response;
import ulaval.glo2003.applicatif.validation.errors.ValidationError;


public class ValidationExceptionResponseBuilder {
    public Response buildFromException(ValidationError exception) {
        var dto = new ErrorDto(exception.getCode(), exception.getMessage());
        return Response.status(exception.getStatus()).entity(dto).build();
    }
}

