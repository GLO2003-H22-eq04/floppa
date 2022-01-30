package ulaval.glo2003;

import java.time.OffsetDateTime;
import java.util.List;

public class SellerInfoResponseDTO {

    public int id;

    public String name;

    public OffsetDateTime createdAt;

    public String bio;

    public List products;

    public SellerInfoResponseDTO(int id, String name, OffsetDateTime createdAt, String bio, List products){

        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.bio = bio;
        this.products = products;
    }

}
