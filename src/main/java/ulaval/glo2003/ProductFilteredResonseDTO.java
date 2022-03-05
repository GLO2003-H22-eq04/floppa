package ulaval.glo2003;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class ProductFilteredResonseDTO {

    public String id;

    public OffsetDateTime createdAt;

    public String title;

    public String description;

    public double suggestedPrice;

    public List<ProductCategory> categories;

    public ProductSellerDTO seller;

    public OffersDTO offers;

    public ProductFilteredResonseDTO(String id,
                                     OffsetDateTime createdAt,
                                     String title,
                                     String description,
                                     double suggestedPrice,
                                     List<ProductCategory> categories,
                                     ProductSellerDTO seller,
                                     OffersDTO offers){
        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;
        this.suggestedPrice = suggestedPrice;
        this.categories = categories;
        this.seller = seller;
        this.offers = offers;
    }
}
