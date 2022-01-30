package ulaval.glo2003.Validation.Errors;

public class JSONParseError extends ValidationError {

    public JSONParseError(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_PARAMETER";
    }
}
