package ulaval.glo2003.Validation;

public class MissingParameterError extends ValidationError {

    public MissingParameterError(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "MISSING_PARAM";
    }
}

