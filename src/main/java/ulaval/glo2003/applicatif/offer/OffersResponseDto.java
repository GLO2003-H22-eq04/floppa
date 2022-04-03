package ulaval.glo2003.applicatif.offer;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import ulaval.glo2003.domain.offer.Offers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OffersResponseDto {

    @JsonbProperty(nillable = true)
    public BigDecimal mean;
    public double min;
    public double max;
    public int count;
    public List<OfferItemResponseDto> items;

    @JsonbCreator
    public OffersResponseDto(@JsonbProperty("min") double min,
                             @JsonbProperty("max") double max,
                             @JsonbProperty(value = "mean", nillable = true) BigDecimal mean,
                             @JsonbProperty("count") int count,
                             @JsonbProperty("items") List<OfferItemResponseDto> items) {
        this.min = min;
        this.max = max;
        this.mean = mean;
        this.count = count;
        this.items = items;
    }

    public static OffersResponseDto fromOffers(Offers offers) {
        return new OffersResponseDto(offers.getMin(), offers.getMax(), offers.getMean().orElse(null), offers.getCount(), getOfferList(offers));
    }

    public static OffersResponseDto empty() {
        return new OffersResponseDto(0, 0, BigDecimal.ZERO, 0, new ArrayList<>());
    }

    private static List<OfferItemResponseDto> getOfferList(Offers offers) {
        List<OfferItemResponseDto> offerList = new ArrayList<>();
        for (var offer : offers.getListOffer()) {
            offerList.add(new OfferItemResponseDto(
                    offer.getName(),
                    offer.getMessage(),
                    offer.getEmail(),
                    offer.getPhoneNumber(),
                    offer.getAmount().getValue()));
        }
        return offerList;
    }
}