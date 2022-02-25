package ulaval.glo2003;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductListRepository implements ProductRepository {

    private final List<Product> productList = new ArrayList<>();

    @Override
    public int add(Product product) {
        productList.add(product);
        return productList.size() - 1;
    }

    @Override
    public Optional<Product> findById(int id) {
        if (productList.size() > id)
            return Optional.of(productList.get(id));

        return Optional.empty();
    }
}
