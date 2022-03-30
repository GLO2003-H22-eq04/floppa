package ulaval.glo2003.applicatif.product;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import ulaval.glo2003.applicatif.offer.OfferInfoResponseDTO;
import ulaval.glo2003.domain.product.ProductCategory;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ProductOfferInfoResponseDTO {

    public UUID id;
    public String title;
    public String description;
    public OffsetDateTime createdAt;
    public double suggestedPrice;
    public List<ProductCategory> categories;
    public OfferInfoResponseDTO offers;

    @JsonbCreator
    public ProductOfferInfoResponseDTO(@JsonbProperty("id") UUID id,
                                       @JsonbProperty("title") String title,
                                       @JsonbProperty("description") String description,
                                       @JsonbProperty("createdAt") OffsetDateTime createdAt,
                                       @JsonbProperty("suggestedPrice") double suggestedPrice,
                                       @JsonbProperty("categories") List<ProductCategory> categories,
                                       @JsonbProperty("offers") OfferInfoResponseDTO offers){

        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.suggestedPrice = suggestedPrice;
        this.categories = categories;
        this.offers = offers;
    }
}
