package ulaval.glo2003;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SellerListRepository implements SellerRepository {

    private List<Seller> sellerList = new ArrayList<>();

    @Override
    public int add(Seller seller) {
        sellerList.add(seller);
        return sellerList.size() - 1;
    }

    @Override
    public Optional<Seller> findById(int id) {
        if (sellerList.size() > id)
            return Optional.of(sellerList.get(id));

        return Optional.empty();
    }

    @Override
    public boolean existById(int id) {
        return findById(id).isPresent();
    }
}
