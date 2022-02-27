package ulaval.glo2003.Validation.Errors;

import jakarta.ws.rs.core.Response;

public class MissingParameterError extends ValidationError {

    public MissingParameterError(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "MISSING_PARAMETER";
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.BAD_REQUEST;
    }
}

