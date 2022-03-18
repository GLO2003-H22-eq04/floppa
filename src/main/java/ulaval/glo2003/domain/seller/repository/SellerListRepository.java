package ulaval.glo2003.domain.seller.repository;

import ulaval.glo2003.domain.seller.Seller;

import java.util.*;

public class SellerListRepository implements SellerRepository {

    private final Map<UUID, Seller> sellers = new HashMap<>();

    @Override
    public UUID add(Seller seller) {
        var id = UUID.randomUUID();
        sellers.put(id, seller);
        return id;
    }

    @Override
    public Optional<Seller> findById(UUID id) {
        return Optional.ofNullable(sellers.get(id));
    }

    @Override
    public boolean existById(UUID id) {
        return findById(id).isPresent();
    }
}


