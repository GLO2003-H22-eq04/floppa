package ulaval.glo2003;

import java.util.Optional;

public interface SellerRepository {

    int add(Seller seller);

    Optional<Seller> findById(int id);
}
