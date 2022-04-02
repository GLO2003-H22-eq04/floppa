package ulaval.glo2003.applicatif.product;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.UUID;

public class ProductSellerDto {

    public UUID id;

    public String name;

    @JsonbCreator
    public ProductSellerDto(@JsonbProperty("id") UUID id, @JsonbProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
}