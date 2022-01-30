package ulaval.glo2003.Validation.Errors;

public class MissingParameterError extends ValidationError {

    public MissingParameterError(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "MISSING_PARAMETER";
    }
}

