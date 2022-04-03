package ulaval.glo2003.domain.product.repository;

import dev.morphia.query.experimental.filters.Filters;
import ulaval.glo2003.domain.config.DatastoreFactory;
import ulaval.glo2003.domain.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductMongodbRepository implements ProductRepository {

    public ProductMongodbRepository() {}

    public ProductMongodbRepository(DatastoreFactory datastoreFactory) {
        this.datastoreFactory = datastoreFactory;
    }

    private DatastoreFactory datastoreFactory;

    @Override
    public UUID add(Product product) {
        if (product.getProductId() == null)
            product.setProductId(UUID.randomUUID());
        datastoreFactory.getDatastore().insert(product);
        return product.getProductId();
    }

    @Override
    public List<Product> findAll() {
        var productQuery = datastoreFactory.getDatastore().find(Product.class);
        return productQuery.stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findById(UUID id) {
        var productQuery = datastoreFactory.getDatastore().find(Product.class).filter(Filters.eq("_id", id));
        return productQuery.stream().findFirst();
    }

    @Override
    public List<Product> productOf(UUID sellerId) {
        var productQuery = datastoreFactory.getDatastore().find(Product.class).filter(Filters.eq("sellerId", sellerId));
        return productQuery.stream().collect(Collectors.toList());
    }
}