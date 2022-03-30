package ulaval.glo2003.api.seller;

import ulaval.glo2003.applicatif.product.ProductOfferInfoResponseDTO;
import ulaval.glo2003.applicatif.seller.CurrentSellerDTO;
import ulaval.glo2003.domain.seller.Seller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

public class SellerAssembler {

    public CurrentSellerDTO currentSellerToDTO(Seller seller, UUID sellerId, List<ProductOfferInfoResponseDTO> productOffer){
        var currentSeller = new CurrentSellerDTO();

        currentSeller.id = sellerId;
        currentSeller.name = seller.getName();
        currentSeller.createdAt = Instant.now().atOffset(ZoneOffset.UTC);
        currentSeller.bio = seller.getBio();
        currentSeller.birthdate = seller.getBirthDate();
        currentSeller.products = productOffer;

        return currentSeller;
    }

}
