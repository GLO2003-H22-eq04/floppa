package ulaval.glo2003.api.validation;

import jakarta.ws.rs.core.Response;
import ulaval.glo2003.api.validation.errors.ValidationError;


public class ValidationExceptionResponseBuilder {
    public Response buildFromException(ValidationError exception) {
        return Response.status(exception.getStatus()).entity(new ErrorDto(exception.getCode(), exception.getMessage())).build();
    }
}

class ErrorDto {
    public String code;
    public String description;

    public ErrorDto(String code, String description) {
        this.code = code;
        this.description = description;
    }
}