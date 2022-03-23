package ulaval.glo2003.domain.offer;

import ulaval.glo2003.api.offer.dto.OffersResponseDTO;
import ulaval.glo2003.domain.product.Amount;

import java.util.ArrayList;
import java.util.List;

public class Offers {

    private double mean;
    private double min;
    private double max;
    private int count;
    private List<OfferItem> items = new ArrayList<>();

    public Offers(OffersResponseDTO offersDTO) {
        this.min = offersDTO.min;
        this.max = offersDTO.max;
        this.mean = offersDTO.mean;
        this.mean = offersDTO.count;

    }

    public void addNewOffer(OfferItem newOffer) {
        items.add(newOffer);
        refresh();
    }

    public void refresh() {
        count++;
        int average = 0;
        for (var item : items) {
            var value = item.getAmount().getValue();

            if (value <= min || min == 0) {
                min = value;
            }
            if (value >= max) {
                max = value;
            }
            average += value;
        }
        this.mean = new Amount(average / count).getValue();
    }

    public double getMean() {
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
