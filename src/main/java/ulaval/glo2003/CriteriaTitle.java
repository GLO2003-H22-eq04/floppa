package ulaval.glo2003;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CriteriaTitle implements Criteria{
    String title;

    public CriteriaTitle(String p_title){
        this.title = p_title;
    }
    @Override
    public List<Product> meetCriteria(List<Product> p_products) {
        List<Product> sellerTitle = new ArrayList<Product>();

        for(Product product : p_products){
            if(Objects.equals(product.getTitle(), title)){
                sellerTitle.add(product);
            }
        }

        return sellerTitle;
    }
}
