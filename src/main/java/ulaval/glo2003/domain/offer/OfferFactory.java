package ulaval.glo2003.domain.offer;

import ulaval.glo2003.api.offer.dto.OfferItemDTO;
import ulaval.glo2003.domain.product.Amount;

public class OfferFactory {

    public OfferItem createNewOffer(OfferItemDTO offerItemDTO){
        var offer = new OfferItem();
        offer.setName(offerItemDTO.name);
        offer.setEmail(offerItemDTO.email);
        offer.setPhoneNumber(offerItemDTO.phoneNumber);

        offer.setAmount(new Amount(offerItemDTO.amount));

        offer.setMessage(offerItemDTO.message);

        return offer;
    }
}
