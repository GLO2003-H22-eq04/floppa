package ulaval.glo2003;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int productId;
    private String title;
    private String description;
    private String sellerId;
    private Amount suggestedPrice;
    private final List<ProductCategory> categories = new ArrayList<>();

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
}
