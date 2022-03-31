package ulaval.glo2003.applicatif.seller;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import ulaval.glo2003.domain.product.Product;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class SellerInfoResponseDto {

    public UUID id;

    public String name;

    public OffsetDateTime createdAt;

    public String bio;

    public List<Product> products;

    @JsonbCreator
    public SellerInfoResponseDto(
            @JsonbProperty("id") UUID id,
            @JsonbProperty("name") String name,
            @JsonbProperty("createdAt") OffsetDateTime createdAt,
            @JsonbProperty("bio") String bio,
            @JsonbProperty("products") List<Product> products
    ) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.bio = bio;
        this.products = products;
    }
}
