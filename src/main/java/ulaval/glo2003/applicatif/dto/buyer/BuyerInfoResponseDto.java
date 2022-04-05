package ulaval.glo2003.applicatif.dto.buyer;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class BuyerInfoResponseDto {
    public String name;
    public String email;
    public String phoneNumber;

    @JsonbCreator
    public BuyerInfoResponseDto(@JsonbProperty("name") String name,
                                @JsonbProperty("email") String email,
                                @JsonbProperty("phoneNumber") String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
