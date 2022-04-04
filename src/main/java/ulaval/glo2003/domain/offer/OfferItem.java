package ulaval.glo2003.domain.offer;

import dev.morphia.annotations.Entity;
import ulaval.glo2003.domain.product.Amount;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
public class OfferItem {
    private UUID offerId;
    private String name;
    private String email;
    private String phoneNumber;
    private Amount amount;
    private String message;
    private Instant createdAt;

    private OfferItem() {
    }

    public OfferItem(OffsetDateTime createdAt) {
        this.createdAt = createdAt.toInstant();
        this.offerId = UUID.randomUUID();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt.toInstant().truncatedTo(ChronoUnit.MILLIS);
    }

    public OffsetDateTime getCreatedAt() {
        return this.createdAt.atOffset(ZoneOffset.UTC);
    }

    public UUID getOfferId() {
        return offerId;
    }

    public void setOfferId(UUID offerId) {
        this.offerId = offerId;
    }
}
