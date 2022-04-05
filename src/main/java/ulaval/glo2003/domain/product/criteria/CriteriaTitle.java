package ulaval.glo2003.domain.product.criteria;

import ulaval.glo2003.domain.product.Product;

import java.util.ArrayList;
import java.util.List;

public class CriteriaTitle implements Criteria {
    String title;

    public CriteriaTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        List<Product> sellerTitle = new ArrayList<Product>();

        for (var product : products)
            if (product.getTitle().toLowerCase().contains(title.toLowerCase())) sellerTitle.add(product);

        return sellerTitle;
    }
}
