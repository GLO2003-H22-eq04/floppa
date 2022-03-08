package ulaval.glo2003.domain.product;

import ulaval.glo2003.api.product.dto.ProductDTO;

public class ProductFactory {

    public Product createProduct(ProductDTO productDTO, String sellerId){
        var product = new Product();
        product.setTitle(productDTO.title);
        product.setDescription(productDTO.description);
        product.setSellerId(sellerId);

        productDTO.categories.forEach(category -> {
            var productCategory = ProductCategory.findByName(category);
            product.addCategory(productCategory);
        });

        product.setSuggestedPrice(new Amount(productDTO.suggestedPrice));

        return product;
    }
}
