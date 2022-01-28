package ulaval.glo2003;

import java.util.ArrayList;
import java.util.List;

public class SellerListRepository implements SellerRepository {

    private List<Seller> sellerList = new ArrayList<>();

    @Override
    public int add(Seller seller) {
        sellerList.add(seller);
        return sellerList.size() - 1;
    }

    @Override
    public Seller findById(int id) {
        return sellerList.get(id);
    }
}
