package ulaval.glo2003.api.offer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ulaval.glo2003.api.validation.HighPriority;
import ulaval.glo2003.api.validation.MinimumPrice;
import ulaval.glo2003.api.validation.errors.InvalidParameterError;
import ulaval.glo2003.api.validation.errors.MissingParameterError;
import ulaval.glo2003.domain.product.Amount;

public class OfferItemDTO {


    @NotNull(message = "Le champ \"name\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @NotBlank(message = "Le champ \"name\" doit contenir une valeur.", payload = InvalidParameterError.class)
    public String name;

    @NotNull(message = "Le champ \"message\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @NotBlank(message = "Le champ \"message\" doit contenir une valeur.", payload = InvalidParameterError.class)
    public String message;

    @NotNull(message = "Le champ \"message\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @NotBlank(message = "Le champ \"message\" doit contenir une valeur.", payload = InvalidParameterError.class)
    public String email;

    @NotNull(message = "Le champ \"message\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @NotEmpty(message = "Le champ \"message\" doit contenir une valeur.", payload = InvalidParameterError.class)
    public int phoneNumber;

    @NotNull(message = "Le champ \"amount\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @MinimumPrice(price = 1, payload = InvalidParameterError.class, message = "Prix invalide.")
    public double amount;

}
