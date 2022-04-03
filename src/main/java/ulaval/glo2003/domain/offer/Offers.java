package ulaval.glo2003.domain.offer;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import ulaval.glo2003.applicatif.offer.OffersResponseDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
public class Offers {
    private List<OfferItem> items = new ArrayList<>();

    public Offers() {
    }

    public Offers(List<OfferItem> items) {
        this.items = items;
    }

    public void addNewOffer(OfferItem newOffer) {
        items.add(newOffer);
    }

    public Optional<BigDecimal> getMean() {
        if (items.isEmpty())
            return Optional.empty();

        double sum = 0;
        for (var item : items) {
            sum += item.getAmount().getValue();
        }

        return Optional.of(BigDecimal.valueOf(sum / items.size()));
    }

    public double getMin() {
        return items.stream().map(value -> value.getAmount().getValue()).min(Double::compareTo).orElse(0d);
    }

    public double getMax() {
        return items.stream().map(value -> value.getAmount().getValue()).max(Double::compareTo).orElse(0d);
    }

    public int getCount() {
        return items.size();
    }

    public List<OfferItem> getListOffer() {
        return items;
    }
}
