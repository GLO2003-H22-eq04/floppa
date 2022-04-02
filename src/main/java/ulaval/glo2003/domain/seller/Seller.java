package ulaval.glo2003.domain.seller;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import ulaval.glo2003.applicatif.seller.SellerDto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity("Seller")
public class Seller {
    @Id
    private UUID id;
    private String name;
    private String bio;
    private LocalDate birthDate;
    private Instant createdAt;

    private Seller() {
    }

    public Seller(UUID id, String name, String bio, LocalDate birthDate, OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.birthDate = birthDate;
    }

    public Seller(SellerDto seller) {
        name = seller.name;
        bio = seller.bio;
        birthDate = seller.birthDate;
        setCreatedAt(OffsetDateTime.now());
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt.atOffset(ZoneOffset.UTC);
    }

    public void setId(UUID sellerId) {
        this.id = sellerId;
    }

    public UUID getId() {
        return id;
    }

    private void setCreatedAt(OffsetDateTime dateTime) {
        createdAt = dateTime.toInstant().truncatedTo(ChronoUnit.MILLIS);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Seller))
            return false;

        var comparedSeller = ((Seller) obj);

        return (comparedSeller.id == null || comparedSeller.id.equals(this.id))
                && comparedSeller.bio.equals(this.bio)
                && comparedSeller.name.equals(this.name)
                && (comparedSeller.createdAt == null || comparedSeller.createdAt.equals(this.createdAt));
    }
}
