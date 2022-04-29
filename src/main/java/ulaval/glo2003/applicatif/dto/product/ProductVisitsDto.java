package ulaval.glo2003.applicatif.dto.product;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class ProductVisitsDto {
    public int visits;

    @JsonbCreator
    public ProductVisitsDto(@JsonbProperty("visits") int visits) {
        this.visits = visits;
    }
}
