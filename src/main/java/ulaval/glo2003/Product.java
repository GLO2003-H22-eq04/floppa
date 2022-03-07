package ulaval.glo2003;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Product {

    private int productId;
    private String title;
    private String description;
    private String sellerId;
    private Amount suggestedPrice;
    private OffsetDateTime createdAt;
    private final List<ProductCategory> categories = new ArrayList<>();


    public Product() {
        createdAt = Instant.now().atOffset(ZoneOffset.UTC);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
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

    public void addCategory(ProductCategory category){
        categories.add(category);
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Amount getSuggestedPrice() {return suggestedPrice;}

    public void setSuggestedPrice(Amount suggestedPrice) {this.suggestedPrice = suggestedPrice;}


    public OffsetDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public List<String> getCategoriesName(){
        return categories.stream().map(ProductCategory::getName).collect(Collectors.toList());
    }
}
