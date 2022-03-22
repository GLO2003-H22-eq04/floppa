package ulaval.glo2003.api.offer.dto;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class OffersDTO {

    public double mean;

    public int count;

    @JsonbCreator
    public OffersDTO(@JsonbProperty("mean") double mean, @JsonbProperty("count") int count){
        this.mean = mean;
        this.count = count;
    }
}