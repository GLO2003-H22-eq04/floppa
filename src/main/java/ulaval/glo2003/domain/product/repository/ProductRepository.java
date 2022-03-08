package ulaval.glo2003.domain.product.repository;

import ulaval.glo2003.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    int add(Product product);
    List<Product> findAll();
    Optional<Product> findById(int id);
    List<Product> productOf(String sellerId);
}
