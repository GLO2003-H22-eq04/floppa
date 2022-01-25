package ulaval.glo2003;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;


public class SellerDTO {
    @NotEmpty(message = "Le champ \"name\" est obligatoire.")
    public String name;

    @NotEmpty(message = "Le champ \"bio\" est obligatoire.")
    public String bio;

    @JsonbDateFormat(value = "yyyy-MM-dd")
    @MinimumAge(age = 18)
    public LocalDate birthDate;
}
