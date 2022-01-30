package ulaval.glo2003.Validation;

public class InvalidParameterError extends ValidationError {

    public InvalidParameterError(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_PARAM";
    }
}
