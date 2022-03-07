package ulaval.glo2003.Criteria;

import ulaval.glo2003.Product;
import ulaval.glo2003.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class CriteriaCategories implements Criteria{
    List<ProductCategory> productCategories;

    public CriteriaCategories(List<ProductCategory> p_productCategories){
        this.productCategories = p_productCategories;
    }
    @Override
    public List<Product> meetCriteria(List<Product> p_products) {
        List<Product> sellerCategory = new ArrayList<Product>();

        for(Product product : p_products){
            for(ProductCategory productCategory : productCategories){
                if(product.getCategories().contains(productCategory)){
                    sellerCategory.add(product);
                    break;
                }
            }
        }

        return  sellerCategory;
    }
}
