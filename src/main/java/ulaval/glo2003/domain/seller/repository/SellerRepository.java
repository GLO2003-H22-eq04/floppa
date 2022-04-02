package ulaval.glo2003.domain.seller.repository;

import ulaval.glo2003.domain.seller.Seller;

import java.util.Optional;
import java.util.UUID;

public interface SellerRepository {

    UUID add(Seller seller);

    Optional<Seller> findById(UUID id);

    boolean existById(UUID id);
}
