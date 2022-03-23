package ulaval.glo2003.applicatif.seller;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class SellerInfoResponseDTO {

    public UUID id;

    public String name;

    public OffsetDateTime createdAt;

    public String bio;

    public List products;

    @JsonbCreator
    public SellerInfoResponseDTO(@JsonbProperty("id") UUID id, @JsonbProperty("name") String name, @JsonbProperty("createdAt") OffsetDateTime createdAt,
                                 @JsonbProperty("bio") String bio, @JsonbProperty("products") List products){
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.bio = bio;
        this.products = products;
    }
}
