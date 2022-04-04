package ulaval.glo2003.applicatif.health;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class HealthDto {

    public final boolean api;
    public final boolean bd;

    @JsonbCreator
    public HealthDto(@JsonbProperty("api") boolean api, @JsonbProperty("bd") boolean bd) {
        this.api = api;
        this.bd = bd;
    }
}
