package ulaval.glo2003;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ulaval.glo2003.Validation.Errors.InvalidParameterError;
import ulaval.glo2003.Validation.Errors.MissingParameterError;
import ulaval.glo2003.Validation.HighPriority;
import ulaval.glo2003.Validation.MinimumPrice;

import java.util.List;

@GroupSequence({HighPriority.class, ProductDTO.class})
public class ProductDTO {

    @NotNull(message = "Le champ \"name\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @NotBlank(message = "Le champ \"name\" doit contenir une valeur.", payload = InvalidParameterError.class)
    public String title;

    @NotNull(message = "Le champ \"name\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @NotBlank(message = "Le champ \"name\" doit contenir une valeur.", payload = InvalidParameterError.class)
    public String description;

    @NotNull(message = "Le champ \"name\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @MinimumPrice(price = 1, payload = InvalidParameterError.class, message = "Prix invalide.")
    public double suggestedPrice;

    public List<String> categories;
}
