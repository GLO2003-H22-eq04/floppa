package ulaval.glo2003.api.product.dto;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.UUID;

public class ProductSellerDTO {

    public UUID id;

    public String name;

    @JsonbCreator
    public ProductSellerDTO(@JsonbProperty("id") UUID id, @JsonbProperty("name") String name){
        this.id = id;
        this.name = name;
    }
}