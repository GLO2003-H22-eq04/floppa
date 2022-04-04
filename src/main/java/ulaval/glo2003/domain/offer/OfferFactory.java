package ulaval.glo2003.domain.offer;

import ulaval.glo2003.applicatif.offer.OfferItemDto;
import ulaval.glo2003.domain.product.Amount;

import java.time.OffsetDateTime;

public class OfferFactory {

    public OfferItem createNewOffer(OfferItemDto offerItemDto) {
        var offer = new OfferItem(OffsetDateTime.now());
        offer.setName(offerItemDto.name);
        offer.setEmail(offerItemDto.email);
        offer.setPhoneNumber(offerItemDto.phoneNumber);
        offer.setAmount(new Amount(offerItemDto.amount));
        offer.setMessage(offerItemDto.message);
        return offer;
    }
}
