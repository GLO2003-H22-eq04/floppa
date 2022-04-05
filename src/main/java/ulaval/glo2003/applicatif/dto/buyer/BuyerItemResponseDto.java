package ulaval.glo2003.applicatif.dto.buyer;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.time.OffsetDateTime;
import java.util.UUID;

public class BuyerItemResponseDto {
    public UUID id;
    public OffsetDateTime createdAt;
    public double amount;
    public String message;
    public BuyerInfoResponseDto buyer;

    @JsonbCreator
    public BuyerItemResponseDto(@JsonbProperty("id") UUID id,
                                @JsonbProperty("createdAt") OffsetDateTime createdAt,
                                @JsonbProperty("amount") double amount,
                                @JsonbProperty("message") String message,
                                @JsonbProperty("buyer") BuyerInfoResponseDto buyer) {
        this.id = id;
        this.createdAt = createdAt;
        this.amount = amount;
        this.message = message;
        this.buyer = buyer;
    }
}
