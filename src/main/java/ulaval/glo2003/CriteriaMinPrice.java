package ulaval.glo2003;

import java.util.ArrayList;
import java.util.List;

public class CriteriaMinPrice implements Criteria{
    float maxPrice;

    public CriteriaMinPrice(float p_maxPrice){
        this.maxPrice = p_maxPrice;
    }
    @Override
    public List<Product> meetCriteria(List<Product> p_products) {
        List<Product> productPrice = new ArrayList<Product>();

        for(Product product : p_products){
            if(product.getSuggestedPrice().getValue() >= maxPrice){
                productPrice.add(product);
            }
        }

        return productPrice;
    }
}
