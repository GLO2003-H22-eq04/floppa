package ulaval.glo2003.applicatif.validation.errors;

import jakarta.ws.rs.core.Response;

public class InvalidParameterError extends ValidationError {

    public InvalidParameterError(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_PARAMETER";
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.BAD_REQUEST;
    }
}
