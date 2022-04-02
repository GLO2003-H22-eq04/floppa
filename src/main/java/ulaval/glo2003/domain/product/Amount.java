package ulaval.glo2003.domain.product;

import dev.morphia.annotations.Entity;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity("Amount")
public class Amount {

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Amount))
            return false;

        var comparedAmount = ((Amount) obj);

        return comparedAmount.value.equals(this.value);
    }

    private BigDecimal value;

    private Amount() {
    }

    @JsonbCreator
    public Amount(@JsonbProperty("value") double value) {
        var decimal = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        this.value = BigDecimal.valueOf(decimal.doubleValue());
    }


    public Amount(BigDecimal value) {
        this.value = value;
    }

    public double getValue() {
        return value.doubleValue();
    }
}
