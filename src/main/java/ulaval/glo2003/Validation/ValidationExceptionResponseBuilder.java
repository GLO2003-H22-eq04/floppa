package ulaval.glo2003.Validation;

import jakarta.ws.rs.core.Response;
import ulaval.glo2003.Validation.Errors.ValidationError;


public class ValidationExceptionResponseBuilder {
    public Response buildFromException(ValidationError exception) {
        return Response.status(exception.getStatus()).entity(new ErrorDTO(exception.getCode(), exception.getMessage())).build();
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