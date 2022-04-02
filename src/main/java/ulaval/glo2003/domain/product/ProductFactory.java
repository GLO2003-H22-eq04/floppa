package ulaval.glo2003.domain.product;

import ulaval.glo2003.applicatif.product.ProductDto;

import java.util.UUID;

public class ProductFactory {

    public Product createProduct(ProductDto productDto, UUID sellerId) {
        var product = new Product();
        product.setTitle(productDto.title);
        product.setDescription(productDto.description);
        product.setSellerId(sellerId);

        productDto.categories.forEach(category -> {
            var productCategory = ProductCategory.findByName(category);
            product.addCategory(productCategory);
        });

        product.setSuggestedPrice(new Amount(productDto.suggestedPrice));

        return product;
    }
}
