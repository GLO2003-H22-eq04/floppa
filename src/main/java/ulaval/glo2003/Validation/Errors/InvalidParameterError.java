package ulaval.glo2003.Validation.Errors;

public class InvalidParameterError extends ValidationError {

    public InvalidParameterError(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_PARAMETER";
    }
}
