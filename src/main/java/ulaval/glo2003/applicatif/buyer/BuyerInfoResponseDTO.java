package ulaval.glo2003.applicatif.buyer;

import jakarta.json.bind.annotation.JsonbProperty;

public class BuyerInfoResponseDTO {
    public String name;
    public String email;
    public String phoneNumber;


    public BuyerInfoResponseDTO(@JsonbProperty("name") String name,
                                @JsonbProperty("email") String email,
                                @JsonbProperty("phoneNumber") String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
