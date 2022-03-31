package ulaval.glo2003.domain.offer;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.mapping.experimental.MorphiaReference;
import ulaval.glo2003.applicatif.offer.OffersResponseDTO;
import ulaval.glo2003.domain.product.Amount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity("Offers")
public class Offers {
    @Id
    private UUID id;
    private Amount mean;
    private Amount min;
    private Amount max;
    private int count;
    private MorphiaReference<List<OfferItem>> items = MorphiaReference.wrap(new ArrayList<>());

    public Offers(OffersResponseDTO offersDTO) {
        this.id = UUID.randomUUID();
    }

    public void addNewOffer(OfferItem newOffer) {
        var item = items.get();
        item.add(newOffer);
        this.items = MorphiaReference.wrap(item);
        refresh();
    }

    private void refresh() {
        count++;
        var newMean = 0;
        for (var item : items.get()) {
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
        if(items.isEmpty())
            return Optional.empty();

        double sum = 0;
        for(var item : items){
            sum += item.getAmount().getValue();
        }

        return Optional.of(BigDecimal.valueOf(sum/items.size()));
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
        return items.get();
    }
}
