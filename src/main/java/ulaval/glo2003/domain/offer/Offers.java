package ulaval.glo2003.domain.offer;

import ulaval.glo2003.api.offer.dto.OffersResponseDTO;
import ulaval.glo2003.domain.product.Amount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Offers {

    private Amount mean;
    private Amount min;
    private Amount max;
    private int count;
    private List<OfferItem> items = new ArrayList<>();

    public Offers(OffersResponseDTO offersDTO) {
        this.min = new Amount(offersDTO.min);
        this.max = new Amount(offersDTO.max);
        this.mean = new Amount(offersDTO.mean.get().doubleValue());
        this.count = offersDTO.count;
    }

    public void addNewOffer(OfferItem newOffer) {
        items.add(newOffer);
        refresh();
    }

    private void refresh() {
        count++;
        var newMean = 0;
        for (var item : items) {
            var value = item.getAmount().getValue();

            if (value <= min.getValue() || min.getValue() == 0) {
                min = new Amount(value);
            }
            if (value >= max.getValue()) {
                max = new Amount(value);
            }
            newMean += value;
        }
        this.mean = new Amount(newMean / count);

    }

    public Optional<BigDecimal> getMean() {
        return Optional.of(BigDecimal.valueOf(mean.getValue()));
    }

    public double getMin() {
        return min.getValue();
    }

    public double getMax() {
        return max.getValue();
    }

    public int getCount() {
        return count;
    }

    public List<OfferItem> getListOffer(){
        return items;
    }
}
