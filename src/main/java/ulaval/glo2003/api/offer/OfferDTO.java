package ulaval.glo2003.api.offer;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class OfferDTO {

    public double mean;

    public int count;

    @JsonbCreator
    public OfferDTO(@JsonbProperty("mean") double mean, @JsonbProperty("count") int count){
        this.mean = mean;
        this.count = count;
    }
}