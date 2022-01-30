package ulaval.glo2003;

public interface SellerRepository {

    int add(Seller seller);

    Seller findById(int id);
}
