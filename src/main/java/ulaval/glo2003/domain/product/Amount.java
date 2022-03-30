package ulaval.glo2003.domain.product;

import dev.morphia.annotations.Entity;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity("Amount")
public class Amount {

    private final double value;

    @JsonbCreator
    public Amount(@JsonbProperty("value") double value){
        var decimal = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        this.value = decimal.doubleValue();
    }

    public double getValue() {
        return value;
    }
}
