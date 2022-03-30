package ulaval.glo2003.domain.seller;

import ulaval.glo2003.applicatif.seller.SellerDTO;

import java.time.*;

public class Seller {

    private final String name;
    private final String bio;
    private final LocalDate birthDate;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

}
