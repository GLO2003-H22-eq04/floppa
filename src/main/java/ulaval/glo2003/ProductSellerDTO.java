package ulaval.glo2003;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class ProductSellerDTO {

    public String id;

    public String name;

    @JsonbCreator
    public ProductSellerDTO(@JsonbProperty("id") String id, @JsonbProperty("name") String name){
        this.id = id;
        this.name = name;
    }
}