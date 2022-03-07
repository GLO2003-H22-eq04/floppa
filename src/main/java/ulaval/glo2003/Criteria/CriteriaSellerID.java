package ulaval.glo2003.Criteria;

import ulaval.glo2003.Product;

import java.util.ArrayList;
import java.util.List;

public class CriteriaSellerID implements Criteria{
    int sellerID;

    public CriteriaSellerID(int p_sellerID){
        this.sellerID = p_sellerID;
    }

    @Override
    public List<Product> meetCriteria(List<Product> p_products) {
        List<Product> sellerIDList = new ArrayList<Product>();

        for(Product product : p_products){
            if(Integer.parseInt(product.getSellerId()) == sellerID){
                sellerIDList.add(product);
            }
        }

        return sellerIDList;
    }
}
