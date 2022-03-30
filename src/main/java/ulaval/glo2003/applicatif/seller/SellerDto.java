package ulaval.glo2003.applicatif.seller;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ulaval.glo2003.api.validation.HighPriority;
import ulaval.glo2003.api.validation.MinimumAge;
import ulaval.glo2003.api.validation.errors.InvalidParameterError;
import ulaval.glo2003.api.validation.errors.MissingParameterError;

import java.time.LocalDate;

@GroupSequence({HighPriority.class, SellerDto.class})
public class SellerDto {

    @NotNull(message = "Le champ \"name\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @NotBlank(message = "Le champ \"name\" doit contenir une valeur.", payload = InvalidParameterError.class)
    public String name;

    @NotNull(message = "Le champ \"bio\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @NotEmpty(message = "Le champ \"bio\" doit contenir une valeur.", payload = InvalidParameterError.class)
    public String bio;


    @NotNull(message = "Le champ \"birthDate\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @MinimumAge(age = 18, payload = InvalidParameterError.class, message = "Date de naissance invalide.")
    public LocalDate birthDate;


}
