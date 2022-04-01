package ulaval.glo2003.domain.product.repository;

import ulaval.glo2003.domain.product.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductListRepository implements ProductRepository {

    private final Map<UUID, Product> products = new HashMap<>();

    @Override
    public UUID add(Product product) {
        UUID id = UUID.randomUUID();
        product.setProductId(id);
        products.put(id, product);
        return id;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> productOf(UUID sellerId) {
        return products.values().stream().filter(p -> p.getSellerId().equals(sellerId)).collect(Collectors.toList());
    }
}
