package ulaval.glo2003.domain.product.criteria;

import ulaval.glo2003.domain.product.Product;

import java.util.ArrayList;
import java.util.List;

public class CriteriaMinPrice implements Criteria {
    double minPrice;

    public CriteriaMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        List<Product> productPrice = new ArrayList<Product>();

        for (Product product : products) {
            if (product.getSuggestedPrice().getValue() >= minPrice) {
                productPrice.add(product);
            }
        }

        return productPrice;
    }
}
