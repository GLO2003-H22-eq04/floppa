package ulaval.glo2003.applicatif.dto.offer;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import ulaval.glo2003.applicatif.dto.buyer.BuyerItemResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OfferInfoResponseDto {
    public Optional<BigDecimal> mean;
    public Optional<BigDecimal> min;
    public Optional<BigDecimal> max;
    public int count;
    public List<BuyerItemResponseDto> items;

    @JsonbCreator
    public OfferInfoResponseDto(@JsonbProperty("min") Optional<BigDecimal> min,
                                @JsonbProperty("max") Optional<BigDecimal> max,
                                @JsonbProperty("mean") Optional<BigDecimal> mean,
                                @JsonbProperty("count") int count,
                                @JsonbProperty("items") List<BuyerItemResponseDto> items) {
        this.min = min;
        this.max = max;
        this.mean = mean;
        this.count = count;
        this.items = items;
    }
}
