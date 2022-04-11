package ulaval.glo2003.applicatif.health;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class HealthDto {

    public final boolean api;
    public final boolean db;

    @JsonbCreator
    public HealthDto(@JsonbProperty("api") boolean api, @JsonbProperty("db") boolean db) {
        this.api = api;
        this.db = db;
    }
}
