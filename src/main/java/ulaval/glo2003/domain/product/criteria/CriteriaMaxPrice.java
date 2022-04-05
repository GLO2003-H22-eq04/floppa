package ulaval.glo2003.domain.product.criteria;

import ulaval.glo2003.domain.product.Product;

import java.util.ArrayList;
import java.util.List;

public class CriteriaMaxPrice implements Criteria {
    double maxPrice;

    public CriteriaMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        var productPrice = new ArrayList<Product>();

        for (var product : products)
            if (product.getSuggestedPrice().getValue() <= maxPrice) productPrice.add(product);

        return productPrice;
    }
}
