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
        List<Product> productPrice = new ArrayList<Product>();

        for (Product product : products) {
            if (product.getSuggestedPrice().getValue() <= maxPrice) {
                productPrice.add(product);
            }
        }

        return productPrice;
    }
}
