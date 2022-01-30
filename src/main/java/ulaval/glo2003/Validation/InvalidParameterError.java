package ulaval.glo2003.Validation;

public class InvalidParameterError extends ValidationError {

    @Override
    public String getCode() {
        return "INVALID_PARAM";
    }
}
