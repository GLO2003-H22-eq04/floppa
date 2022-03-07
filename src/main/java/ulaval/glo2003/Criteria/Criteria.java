package ulaval.glo2003.Criteria;
import ulaval.glo2003.Product;

import java.util.List;

public interface Criteria {
    public List<Product> meetCriteria(List<Product> products);
}
