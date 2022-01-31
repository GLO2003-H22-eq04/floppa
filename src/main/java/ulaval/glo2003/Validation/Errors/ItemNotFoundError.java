package ulaval.glo2003.Validation.Errors;

public class ItemNotFoundError extends ValidationError{
    public ItemNotFoundError(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "ITEM_NOT_FOUND";
    }
}
