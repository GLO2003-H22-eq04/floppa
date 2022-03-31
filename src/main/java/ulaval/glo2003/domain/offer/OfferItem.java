package ulaval.glo2003.domain.offer;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import org.joda.time.DateTime;
import ulaval.glo2003.domain.product.Amount;

import java.util.UUID;

@Entity("OfferItem")
public class OfferItem {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private Amount amount;
    private String message;
    @Transient
    private DateTime createdAt;

    public OfferItem() {
        this.createdAt = DateTime.now();
        id = UUID.randomUUID();
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

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getCreatedAt() {
        return this.createdAt;
    }
}
