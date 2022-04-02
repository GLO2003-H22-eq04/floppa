package ulaval.glo2003.domain.offer;

import ulaval.glo2003.applicatif.offer.OfferItemDto;
import ulaval.glo2003.domain.product.Amount;

public class OfferFactory {

    public OfferItem createNewOffer(OfferItemDto offerItemDto) {
        var offer = new OfferItem();
        offer.setName(offerItemDto.name);
        offer.setEmail(offerItemDto.email);
        offer.setPhoneNumber(offerItemDto.phoneNumber);
        offer.setAmount(new Amount(offerItemDto.amount));
        offer.setMessage(offerItemDto.message);
        return offer;
    }
}
