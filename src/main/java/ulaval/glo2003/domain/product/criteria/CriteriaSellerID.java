package ulaval.glo2003.domain.product.criteria;

import ulaval.glo2003.domain.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CriteriaSellerID implements Criteria{
    UUID sellerID;

    public CriteriaSellerID(UUID p_sellerID){
        this.sellerID = p_sellerID;
    }

    @Override
    public List<Product> meetCriteria(List<Product> p_products) {
        List<Product> sellerIDList = new ArrayList<Product>();

        for(Product product : p_products){
            if(product.getSellerId().equals(sellerID)){
                sellerIDList.add(product);
            }
        }

        return sellerIDList;
    }
}
