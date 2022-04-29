package ulaval.glo2003.applicatif.dto.seller;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import ulaval.glo2003.applicatif.dto.product.ProductOfSellerDto;
import ulaval.glo2003.domain.product.Product;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SellerInfoResponseDto {

    public UUID id;

    public String name;

    public OffsetDateTime createdAt;

    public String bio;

    public LocalDate birthdate;

    public Collection<ProductOfSellerDto> products;
}
