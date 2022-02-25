package ulaval.glo2003;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int id;
    private String title;
    private String description;
    private ProductSellerDTO seller;
    private Amount suggestedPrice;
    private final List<ProductCategory> categories = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ProductSellerDTO getSeller() {
        return seller;
    }

    public void setSeller(ProductSellerDTO seller) {
        this.seller = seller;
    }

    public Amount getSuggestedPrice() {return suggestedPrice;}

    public void setSuggestedPrice(Amount suggestedPrice) {this.suggestedPrice = suggestedPrice;}
}
