package ulaval.glo2003.applicatif.product;

import ulaval.glo2003.applicatif.offer.OffersResponseDto;
import ulaval.glo2003.domain.product.Amount;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ProductInfoResponseDto {

    public UUID id;

    public OffsetDateTime createdAt;

    public String title;

    public String description;

    public Amount suggestedPrice;

    public List<String> categories;

    public ProductSellerDto seller;

    public OffersResponseDto offers;
}
