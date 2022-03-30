package ulaval.glo2003.applicatif.buyer;

import jakarta.json.bind.annotation.JsonbProperty;
import org.joda.time.DateTime;

import java.util.UUID;

public class BuyerItemResponseDTO {
    public UUID id;
    public DateTime createdAt;
    public double amount;
    public String message;
    public BuyerInfoResponseDTO buyer;


    public BuyerItemResponseDTO(@JsonbProperty("createdAt") DateTime createdAt,
                                @JsonbProperty("amount") double amount,
                                @JsonbProperty("message") String message,
                                @JsonbProperty("buyer") BuyerInfoResponseDTO buyer){
        this.createdAt = createdAt;
        this.amount = amount;
        this.message = message;
        this.buyer = buyer;
    }
}
