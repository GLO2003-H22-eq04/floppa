package ulaval.glo2003.domain.seller.repository;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Reference;
import ulaval.glo2003.domain.seller.Seller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Entity("SellerListRepository")
public class SellerListRepository implements SellerRepository {
    @Reference
    private final Map<UUID, Seller> sellers = new HashMap<>();

    @Override
    public UUID add(Seller seller) {
        var id = UUID.randomUUID();
        seller.setId(id);
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