package ulaval.glo2003.domain.offer;

import ulaval.glo2003.api.offer.dto.OffersResponseDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Offers {

    private Optional<BigDecimal> mean;
    private double min;
    private double max;
    private int count;
    private List<OfferItem> items = new ArrayList<>();

    public Offers(OffersResponseDTO offersDTO) {
        this.min = offersDTO.min;
        this.max = offersDTO.max;
        this.mean = offersDTO.mean;
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

            if (value <= min || min == 0) {
                min = value;
            }
            if (value >= max) {
                max = value;
            }
            newMean += value;
        }
        this.mean = Optional.of(BigDecimal.valueOf(newMean / count));

    }

    public Optional<BigDecimal> getMean() {
        return mean;
    }
    public double getMin() {
        return min;
    }
    public double getMax() {
        return max;
    }
    public int getCount() {
        return count;
    }
    public List<OfferItem> getListOffer(){
        return items;
    }
}
