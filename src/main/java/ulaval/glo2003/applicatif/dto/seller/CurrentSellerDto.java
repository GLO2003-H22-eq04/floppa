package ulaval.glo2003.applicatif.dto.seller;

import ulaval.glo2003.applicatif.dto.product.ProductOfferInfoResponseDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class CurrentSellerDto {

    public UUID id;
    public String name;
    public OffsetDateTime createdAt;
    public String bio;
    public LocalDate birthdate;
    public List<ProductOfferInfoResponseDto> products;
}
