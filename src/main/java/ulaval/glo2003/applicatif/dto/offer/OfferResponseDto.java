package ulaval.glo2003.applicatif.dto.offer;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.math.BigDecimal;
import java.util.Optional;

public class OfferResponseDto {

    public Optional<BigDecimal> mean;
    public int count;

    @JsonbCreator
    public OfferResponseDto(@JsonbProperty("mean") Optional<BigDecimal> mean,
                            @JsonbProperty("count") int count) {
        this.mean = mean;
        this.count = count;
    }
}
