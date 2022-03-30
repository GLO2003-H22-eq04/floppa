package ulaval.glo2003.domain.product.criteria;

import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class CriteriaCategories implements Criteria {
    List<ProductCategory> productCategories;

    public CriteriaCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        List<Product> sellerCategory = new ArrayList<Product>();

        for (Product product : products) {
            for (ProductCategory productCategory : productCategories) {
                if (product.getCategories().contains(productCategory)) {
                    sellerCategory.add(product);
                    break;
                }
            }
        }

        return sellerCategory;
    }
}
