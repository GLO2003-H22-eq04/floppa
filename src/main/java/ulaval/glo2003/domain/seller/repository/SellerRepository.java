package ulaval.glo2003.domain.seller.repository;

import ulaval.glo2003.domain.seller.Seller;

import java.util.Optional;

public interface SellerRepository {

    int add(Seller seller);

    Optional<Seller> findById(int id);

    boolean existById(int id);
}
