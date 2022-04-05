package ulaval.glo2003.domain.product;

import ulaval.glo2003.applicatif.dto.product.ProductDto;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

public class ProductFactory {

    public Product createProduct(ProductDto productDto, UUID sellerId) {
        var product = new Product(Instant.now().atOffset(ZoneOffset.UTC));
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
