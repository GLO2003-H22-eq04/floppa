package ulaval.glo2003;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.OffsetDateTime;
import java.util.List;

public class ProductFilteredResponseDTO {

    public String id;

    public OffsetDateTime createdAt;

    public String title;

    public String description;

    public double suggestedPrice;

    public List<ProductCategory> categories;

    public ProductSellerDTO seller;

    public OffersDTO offers;

    public ProductFilteredResponseDTO() {
    }

    public ProductFilteredResponseDTO(String id,
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
