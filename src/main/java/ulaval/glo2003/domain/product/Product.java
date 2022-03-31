package ulaval.glo2003.domain.product;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import dev.morphia.annotations.Transient;
import dev.morphia.mapping.experimental.MorphiaReference;
import ulaval.glo2003.applicatif.offer.OffersResponseDTO;
import ulaval.glo2003.domain.offer.Offers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity("Product")
public class Product {
    @Id
    private UUID productId;
    private String title;
    private String description;
    private UUID sellerId;
    private Amount suggestedPrice;
    @Transient
    private OffsetDateTime createdAt;
    private Offers offers;
    private final List<ProductCategory> categories = new ArrayList<>();


    public Product() {
        createdAt = Instant.now().atOffset(ZoneOffset.UTC);
        this.offers = new Offers(OffersResponseDto.empty());
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void addCategory(ProductCategory category) {
        categories.add(category);
    }

    public UUID getSellerId() {
        return sellerId;
    }

    public void setSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    public Amount getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(Amount suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getCategoriesName() {
        return categories.stream().map(ProductCategory::getName).collect(Collectors.toList());
    }

    public void setOffers(Offers offers) {
        this.offers = offers;
    }

    public Offers getOffers() {
        return offers;
    }

    public UUID getID() {
        return this.productId;
    }
}
