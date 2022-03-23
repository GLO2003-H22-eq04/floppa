package ulaval.glo2003.api.offer.dto;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OffersResponseDTO {

    public Optional<BigDecimal> mean;
    public double min;
    public double max;
    public int count;
    public List<OfferItemResponseDTO> items;

    @JsonbCreator
    public OffersResponseDTO(@JsonbProperty("min") double min,
                             @JsonbProperty("max") double max,
                             @JsonbProperty("mean") Optional<BigDecimal> mean,
                             @JsonbProperty("count") int count,
                             @JsonbProperty("items") List<OfferItemResponseDTO> items) {
        this.min = min;
        this.max = max;
        this.mean = mean;
        this.count = count;
        this.items = items;
    }

    public static OffersResponseDTO empty(){
        return new OffersResponseDTO(0,0,Optional.of(BigDecimal.ZERO),0, new ArrayList<>());
    }
}