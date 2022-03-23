package ulaval.glo2003.api.offer.dto;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import java.util.List;

public class OffersResponseDTO {

    public double mean;
    public double min;
    public double max;
    public int count;
    public List<OfferItemResponseDTO> items;

    @JsonbCreator
    public OffersResponseDTO(@JsonbProperty("min") double min,
                             @JsonbProperty("max") double max,
                             @JsonbProperty("mean") double mean,
                             @JsonbProperty("count") int count,
                             @JsonbProperty("items") List<OfferItemResponseDTO> items) {
        this.min = min;
        this.max = max;
        this.mean = mean;
        this.count = count;
        this.items = items;
    }
}