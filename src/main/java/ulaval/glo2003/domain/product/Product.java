package ulaval.glo2003.domain.product;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import ulaval.glo2003.domain.offer.Offers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
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

    private Instant createdAt;
    private Offers offers;
    private int visits;
    private final List<ProductCategory> categories = new ArrayList<>();

    public Product(OffsetDateTime createdAt) {
        setCreatedAt(createdAt);
        this.offers = new Offers();
    }

    // Est utilisé par Morphia même si il est privé car java c'est dumb.
    private Product() {
        this.offers = new Offers();
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
        return createdAt.atOffset(ZoneOffset.UTC);
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt.toInstant().truncatedTo(ChronoUnit.MILLIS);
    }

    public List<String> getCategoriesName() {
        return categories.stream().map(ProductCategory::getName).collect(Collectors.toList());
    }

    public void setOffers(Offers offers) {
        this.offers = offers;
    }

    public void setVisits(){this.visits++;}

    public Offers getOffers() {
        return offers;
    }

    public int getVisits(){
        return visits;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Product))
            return false;

        var comparedProduct = (Product) obj;

        return (comparedProduct.productId == null || comparedProduct.productId.equals(this.productId))
                && comparedProduct.createdAt.equals(this.createdAt)
                && comparedProduct.description.equals(this.description)
                && comparedProduct.suggestedPrice.equals(this.suggestedPrice)
                && comparedProduct.title.equals(this.title);
    }
}
