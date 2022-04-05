package ulaval.glo2003.domain.product.criteria;

import ulaval.glo2003.domain.product.Product;

import java.util.List;

public interface Criteria {
    List<Product> meetCriteria(List<Product> products);
}
