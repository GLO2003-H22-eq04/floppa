package ulaval.glo2003;

import java.util.List;

public class Product {

    private String title;
    private String description;
    private double suggestedPrice;
    private List<ProductCategory> categories;

    public Product(ProductDTO product){
        title = product.title;
        description = product.description;
        suggestedPrice = product.suggestedPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getSuggestedPrice() {
        return suggestedPrice;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }
}
