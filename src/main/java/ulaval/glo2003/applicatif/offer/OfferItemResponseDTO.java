package ulaval.glo2003.applicatif.offer;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class OfferItemResponseDTO {

    public String name;
    public String message;
    public String email;
    public String phoneNumber;
    public double amount;

    @JsonbCreator
    public OfferItemResponseDTO(@JsonbProperty("name") String name,
                                @JsonbProperty("message") String message,
                                @JsonbProperty("email") String email,
                                @JsonbProperty("phoneNumber") String phoneNumber,
                                @JsonbProperty("amount") double amount){
        this.name = name;
        this.message = message;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
    }
}
