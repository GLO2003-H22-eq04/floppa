package ulaval.glo2003.domain.seller;

import ulaval.glo2003.applicatif.seller.SellerDto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Seller {

    private final String name;
    private final String bio;
    private final LocalDate birthDate;
    private final OffsetDateTime createdAt;


    public Seller(SellerDto seller) {
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

}
