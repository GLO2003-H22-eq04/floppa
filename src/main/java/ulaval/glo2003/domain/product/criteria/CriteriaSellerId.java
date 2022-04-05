package ulaval.glo2003.domain.product.criteria;

import ulaval.glo2003.domain.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CriteriaSellerId implements Criteria {
    UUID sellerId;

    public CriteriaSellerId(UUID sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        List<Product> sellerIdList = new ArrayList<Product>();

        for (var product : products) if (product.getSellerId().equals(sellerId)) sellerIdList.add(product);

        return sellerIdList;
    }
}
