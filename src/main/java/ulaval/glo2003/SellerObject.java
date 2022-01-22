package ulaval.glo2003;

import jakarta.json.bind.annotation.JsonbDateFormat;

import java.time.LocalDate;
import java.util.Date;

public class SellerObject {
    public String name;
    public String bio;

    @JsonbDateFormat(value = "yyyy-MM-dd")
    public LocalDate birthDate;
}
