package ulaval.glo2003;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Seller {

    private String name;
    private String bio;
    private LocalDate birthDate;
    private Date createdAt;


    public Seller(SellerDTO seller){
        name = seller.name;
        bio = seller.bio;
        birthDate = seller.birthDate;
        createdAt = new Date();
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public List getProducts() {
        return new ArrayList<>();
    }

}
