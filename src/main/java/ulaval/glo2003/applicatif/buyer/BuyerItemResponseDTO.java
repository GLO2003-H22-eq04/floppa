package ulaval.glo2003.applicatif.buyer;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import org.joda.time.DateTime;

import java.time.OffsetDateTime;
import java.util.UUID;

public class BuyerItemResponseDTO {
    public UUID id;
    public OffsetDateTime createdAt;
    public double amount;
    public String message;
    public BuyerInfoResponseDTO buyer;

    @JsonbCreator
    public BuyerItemResponseDTO(@JsonbProperty("createdAt") OffsetDateTime createdAt,
                                @JsonbProperty("amount") double amount,
                                @JsonbProperty("message") String message,
                                @JsonbProperty("buyer") BuyerInfoResponseDTO buyer){
        this.createdAt = createdAt;
        this.amount = amount;
        this.message = message;
        this.buyer = buyer;
    }
}
