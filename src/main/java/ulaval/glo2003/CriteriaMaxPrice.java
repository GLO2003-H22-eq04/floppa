package ulaval.glo2003;

import java.util.ArrayList;
import java.util.List;

public class CriteriaMaxPrice implements Criteria{
    int maxPrice;

    public CriteriaMaxPrice(int p_maxPrice){
        this.maxPrice = p_maxPrice;
    }
    @Override
    public List<Product> meetCriteria(List<Product> p_products) {
        List<Product> sellerIDList = new ArrayList<Product>();

        for(Product product : p_products){
            if(product.getSuggestedPrice().getValue() <= maxPrice){
                sellerIDList.add(product);
            }
        }

        return sellerIDList;
    }
}
