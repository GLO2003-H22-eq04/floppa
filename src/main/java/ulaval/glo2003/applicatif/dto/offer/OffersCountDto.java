package ulaval.glo2003.applicatif.dto.offer;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class OffersCountDto {
    public int count;

    @JsonbCreator
    public OffersCountDto(@JsonbProperty("count") int count) {
        this.count = count;
    }
}
