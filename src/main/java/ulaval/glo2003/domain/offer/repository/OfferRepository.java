package ulaval.glo2003.domain.offer.repository;

import ulaval.glo2003.domain.offer.Offers;

import java.util.UUID;

public interface OfferRepository {

    UUID add(Offers offers);
}
