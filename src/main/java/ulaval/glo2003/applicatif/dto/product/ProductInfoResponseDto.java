package ulaval.glo2003.applicatif.dto.product;

import ulaval.glo2003.applicatif.dto.offer.OffersResponseDto;
import ulaval.glo2003.domain.product.Amount;
import ulaval.glo2003.domain.product.ProductCategory;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ProductInfoResponseDto {

    public UUID id;

    public OffsetDateTime createdAt;

    public String title;

    public String description;

    public Double suggestedPrice;

    public List<ProductCategory> categories;

    public ProductSellerDto seller;

    public OffersResponseDto offers;

    public int visits;
}
