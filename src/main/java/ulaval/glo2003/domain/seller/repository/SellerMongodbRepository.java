package ulaval.glo2003.domain.seller.repository;

import dev.morphia.query.experimental.filters.Filters;
import jakarta.inject.Inject;
import ulaval.glo2003.domain.config.DatastoreFactory;
import ulaval.glo2003.domain.seller.Seller;

import java.util.Optional;
import java.util.UUID;

public class SellerMongodbRepository implements SellerRepository {

    public SellerMongodbRepository() {
    }

    public SellerMongodbRepository(DatastoreFactory datastoreFactory) {
        this.datastoreFactory = datastoreFactory;
    }

    private DatastoreFactory datastoreFactory;

    @Override
    public UUID add(Seller seller) {
        if (seller.getID() == null)
            seller.setId(UUID.randomUUID());
        datastoreFactory.getDatastore().save(seller);
        return seller.getID();
    }

    @Override
    public Optional<Seller> findById(UUID id) {
        var sellerQuery = datastoreFactory.getDatastore().find(Seller.class).filter(Filters.eq("_id", id));
        return sellerQuery.stream().findFirst();
    }

    @Override
    public boolean existById(UUID id) {
        return findById(id).isPresent();
    }
}