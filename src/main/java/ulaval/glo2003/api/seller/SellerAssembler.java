package ulaval.glo2003.api.seller;

import ulaval.glo2003.applicatif.product.ProductOfferInfoResponseDto;
import ulaval.glo2003.applicatif.seller.CurrentSellerDto;
import ulaval.glo2003.domain.seller.Seller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

public class SellerAssembler {

    public CurrentSellerDto currentSellerToDTO(Seller seller, UUID sellerId, List<ProductOfferInfoResponseDto> productOffer){
        var currentSeller = new CurrentSellerDto();

        currentSeller.id = sellerId;
        currentSeller.name = seller.getName();
        currentSeller.createdAt = Instant.now().atOffset(ZoneOffset.UTC);
        currentSeller.bio = seller.getBio();
        currentSeller.birthdate = seller.getBirthDate();
        currentSeller.products = productOffer;

        return currentSeller;
    }

}
