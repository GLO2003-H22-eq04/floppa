package ulaval.glo2003.domain.product.criteria;

import ulaval.glo2003.domain.product.Product;

import java.util.List;

public interface Criteria {
    public List<Product> meetCriteria(List<Product> products);
}
