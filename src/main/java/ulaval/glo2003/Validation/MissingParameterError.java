package ulaval.glo2003.Validation;

public class MissingParameterError extends ValidationError {

    @Override
    public String getCode() {
        return "MISSING_PARAM";
    }
}

