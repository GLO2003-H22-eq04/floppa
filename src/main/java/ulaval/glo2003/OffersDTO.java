package ulaval.glo2003;

public class OffersDTO {
    public double mean;
    public int count;


    public OffersDTO(Amount mean, int count) {
        this.mean = mean.getValue();
        this.count = count;
    }


}
