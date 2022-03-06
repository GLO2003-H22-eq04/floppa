package ulaval.glo2003.Validation.Errors;

import jakarta.ws.rs.core.Response;

public class ItemNotFoundError extends ValidationError {
    public ItemNotFoundError(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "ITEM_NOT_FOUND";
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.NOT_FOUND;
    }
}
