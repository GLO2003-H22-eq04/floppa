package ulaval.glo2003.domain.product.repository;

import ulaval.glo2003.domain.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    UUID add(Product product);

    List<Product> findAll();

    Optional<Product> findById(UUID id);

    List<Product> productOf(UUID sellerId);
}
