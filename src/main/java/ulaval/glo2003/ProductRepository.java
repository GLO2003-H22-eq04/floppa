package ulaval.glo2003;

import java.util.Optional;

public interface ProductRepository {

    int add(Product product);

    Optional<Product> findById(int id);
}
