package ulaval.glo2003;

import java.text.DecimalFormat;

public class Amount {

    private static final DecimalFormat FORMATTER = new DecimalFormat("#.##");
    private final double value;

    public Amount(double value){
        this.value = Double.parseDouble(FORMATTER.format(value));
    }

    public double getValue() {
        return value;
    }
}
