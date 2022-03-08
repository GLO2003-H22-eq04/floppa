package ulaval.glo2003;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductListRepository implements ProductRepository {

    private final List<Product> productList = new ArrayList<>();

    @Override
    public int add(Product product) {
        productList.add(product);
        return productList.size() - 1;
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public Optional<Product> findById(int id) {
        if (productList.size() > id)
            return Optional.of(productList.get(id));

        return Optional.empty();
    }

    @Override
    public List<Product> productOf(String sellerId) {
        return productList.stream().filter(p -> p.getSellerId().equals(String.valueOf(sellerId))).collect(Collectors.toList());
    }
}
