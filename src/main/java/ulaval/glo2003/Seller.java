package ulaval.glo2003;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotEmpty;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class Seller {

    private String name;
    private String bio;
    private LocalDate birthDate;
    private OffsetDateTime createdAt;


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

    public List getProducts() {
        return new ArrayList<>();
    }

}
