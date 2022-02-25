package ulaval.glo2003;

public class SellerFactory {

    public ProductSellerDTO createSeller(String id, String name){
        var productSeller = new ProductSellerDTO();
        productSeller.setId(id);
        productSeller.setName(name);
        return productSeller;
    }
}
