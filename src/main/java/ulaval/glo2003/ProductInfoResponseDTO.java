package ulaval.glo2003;

import java.time.OffsetDateTime;
import java.util.List;

public class ProductInfoResponseDTO {

    public String id;

    public OffsetDateTime createdAt;

    public String title;

    public String description;

    public double suggestedPrice;

    public List<String> categories;


}
