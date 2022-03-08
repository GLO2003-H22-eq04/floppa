package ulaval.glo2003;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Amount {

    private final double value;

    public Amount(double value){
        var decimal = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        this.value = decimal.doubleValue();
    }

    public double getValue() {
        return value;
    }
}
