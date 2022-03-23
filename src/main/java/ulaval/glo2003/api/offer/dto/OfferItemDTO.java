package ulaval.glo2003.api.offer.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ulaval.glo2003.api.validation.errors.InvalidParameterError;
import ulaval.glo2003.api.validation.errors.MissingParameterError;
import ulaval.glo2003.api.validation.*;

@GroupSequence({HighPriority.class, OfferItemDTO.class})
public class OfferItemDTO {


    @NotNull(message = "Le champ \"name\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @NotBlank(message = "Le champ \"name\" doit contenir une valeur.", payload = InvalidParameterError.class)
    public String name;

    @NotNull(message = "Le champ \"message\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @MinimumCharacter(nbrCharater = 100,payload = InvalidParameterError.class, message = "Message Invalide.")
    public String message;

    @NotNull(message = "Le champ \"message\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    //@RequirementEmail(message = "Message invalide.", payload = InvalidParameterError.class)
    @Email(message = "Email Invalide.", payload = InvalidParameterError.class)
    public String email;

    @NotNull(message = "Le champ \"message\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @RequirementPhoneNum(message = "Le champ \"phoneNumber\" est invalide.",payload = InvalidParameterError.class)
    public String phoneNumber;

    @NotNull(message = "Le champ \"amount\" est obligatoire.", payload = MissingParameterError.class, groups = HighPriority.class)
    @MinimumPrice(price = 1, payload = InvalidParameterError.class, message = "Prix invalide.")
    public double amount;

}
