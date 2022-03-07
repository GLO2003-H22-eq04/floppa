package ulaval.glo2003;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    int add(Product product);
    List<Product> findAll();
    Optional<Product> findById(int id);
}
