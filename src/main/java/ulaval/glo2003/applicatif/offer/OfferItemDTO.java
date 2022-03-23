package ulaval.glo2003.applicatif.offer;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import ulaval.glo2003.api.validation.errors.InvalidParameterError;
import ulaval.glo2003.api.validation.errors.MissingParameterError;
import ulaval.glo2003.api.validation.*;

@GroupSequence({HighPriority.class, OfferItemDTO.class})
public class OfferItemDTO {


    @NotNull(message = "Le champ \"name\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @NotBlank(message = "Le champ \"name\" doit contenir une valeur.", payload = InvalidParameterError.class)
    public String name;

    @NotNull(message = "Le champ \"message\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @Length(min = 100, payload = InvalidParameterError.class, message = "Message Invalide.")
    public String message;

    @NotNull(message = "Le champ \"message\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email Invalide.", payload = InvalidParameterError.class)
    public String email;

    @NotNull(message = "Le champ \"message\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = "Le champ \"phoneNumber\" est invalide.", payload = InvalidParameterError.class)
    public String phoneNumber;

    @NotNull(message = "Le champ \"amount\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @MinimumPrice(price = 1, payload = InvalidParameterError.class, message = "Prix invalide.")
    public double amount;

}
