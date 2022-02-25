package ulaval.glo2003;

public class ProductFactory {

    public Product createProduct(ProductDTO productDTO, ProductSellerDTO sellerDTO){
        var product = new Product();
        product.setTitle(productDTO.title);
        product.setDescription(productDTO.description);
        product.setSeller(sellerDTO);

        var categories = productDTO.categories;
        if(categories != null && !categories.isEmpty()){
            categories.forEach(category -> {
                var productCategory = ProductCategory.findByName(category);
                product.addCategory(productCategory);
            });
        }

        product.setSuggestedPrice(new Amount(productDTO.suggestedPrice));

        return product;
    }
}
