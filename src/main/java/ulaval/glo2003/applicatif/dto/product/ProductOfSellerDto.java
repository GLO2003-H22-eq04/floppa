package ulaval.glo2003.applicatif.dto.product;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import ulaval.glo2003.applicatif.dto.offer.OfferResponseDto;
import ulaval.glo2003.domain.product.ProductCategory;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ProductOfSellerDto {
    public UUID id;
    public OffsetDateTime createdAt;
    public String title;
    public String description;
    public List<ProductCategory> categories;
    public double suggestedPrice;
    public OfferResponseDto offers;

    @JsonbCreator
    public ProductOfSellerDto(@JsonbProperty("id") UUID id,
                              @JsonbProperty("createdAt") OffsetDateTime createdAt,
                              @JsonbProperty("title") String title,
                              @JsonbProperty("description") String description,
                              @JsonbProperty("suggestedPrice") double suggestedPrice,
                              @JsonbProperty("categories") List<ProductCategory> categories,
                              @JsonbProperty("offers") OfferResponseDto offers) {

        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;
        this.suggestedPrice = suggestedPrice;
        this.categories = categories;
        this.offers = offers;
    }
}
