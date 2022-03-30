package ulaval.glo2003.domain.seller;

import ulaval.glo2003.applicatif.seller.SellerDto;
import ulaval.glo2003.domain.product.Product;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class Seller {

    private final String name;
    private final String bio;
    private final LocalDate birthDate;
    private final OffsetDateTime createdAt;
    private final List<Product> productList = new ArrayList<>();


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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Product> getProductList() { return productList; }

}
