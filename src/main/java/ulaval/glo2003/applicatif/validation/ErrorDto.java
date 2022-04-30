package ulaval.glo2003.applicatif.validation;

public class ErrorDto {
    public String code;
    public String description;

    public ErrorDto(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public ErrorDto() {
    }
}
