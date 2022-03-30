package ulaval.glo2003.applicatif.product;


import ulaval.glo2003.applicatif.offer.OffersResponseDTO;
import ulaval.glo2003.domain.product.ProductCategory;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ProductFilteredResponseDTO {

    public UUID id;

    public OffsetDateTime createdAt;

    public String title;

    public String description;

    public double suggestedPrice;

    public List<ProductCategory> categories;

    public ProductSellerDTO seller;

    public OffersResponseDTO offers;

    public ProductFilteredResponseDTO(UUID id,
                                      OffsetDateTime createdAt,
                                      String title,
                                      String description,
                                      double suggestedPrice,
                                      List<ProductCategory> categories,
                                      ProductSellerDTO seller,
                                      OffersResponseDTO offers){
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
