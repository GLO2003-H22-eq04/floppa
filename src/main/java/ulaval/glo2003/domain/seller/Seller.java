package ulaval.glo2003.domain.seller;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import ulaval.glo2003.applicatif.seller.SellerDTO;

import java.time.*;
import java.util.UUID;

@Entity("Seller")
public class Seller {
    @Id
    private UUID id;
    private final String name;
    private final String bio;
    private final LocalDate birthDate;
    @Transient
    private final OffsetDateTime createdAt;


    public Seller(SellerDTO seller){
        name = seller.name;
        bio = seller.bio;
        birthDate = seller.birthDate;
        createdAt = Instant.now().atOffset(ZoneOffset.UTC);
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(UUID sellerId) {
        this.id = sellerId;
    }

    public UUID getID() {
        return id;
    }
}
