package ulaval.glo2003;

import java.time.OffsetDateTime;
import java.util.List;

public class ProductInfoResponseDTO {

    public String id;

    public OffsetDateTime createdAt;

    public String title;

    public String description;

    public Amount suggestedPrice;

    public List<String> categories;

    public ProductSellerDTO seller;

    public OfferDTO offers;
}
