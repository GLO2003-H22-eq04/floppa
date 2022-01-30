package ulaval.glo2003.Validation;

import jakarta.annotation.ManagedBean;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.beans.JavaBean;


public class ValidationExceptionResponseBuilder {
    public Response buildFromException(ValidationError exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(exception.getCode(), exception.getMessage())).build();
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