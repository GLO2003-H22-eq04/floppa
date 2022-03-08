package ulaval.glo2003;

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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

}
