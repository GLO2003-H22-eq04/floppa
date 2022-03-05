package ulaval.glo2003.Criteria;

import ulaval.glo2003.Product;

import java.util.ArrayList;
import java.util.List;

public class CriteriaTitle implements Criteria{
    String title;

    public CriteriaTitle(String p_title){
        this.title = p_title;
    }
    @Override
    public List<Product> meetCriteria(List<Product> p_products) {
        List<Product> sellerTitle = new ArrayList<Product>();

        for(Product product : p_products){
            if(product.getTitle().toLowerCase().contains(title.toLowerCase())){
                sellerTitle.add(product);
            }
        }

        return sellerTitle;
    }
}
