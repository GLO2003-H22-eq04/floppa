package ulaval.glo2003.api.product;

import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.applicatif.product.ProductDto;
import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.ProductCategory;
import ulaval.glo2003.domain.product.ProductFactory;

import java.util.List;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;

public class ProductFactoryTests {

    private static final UUID SELLER_ID = UUID.fromString("b68a867d-9d27-42a9-afd4-9ff1eb5334ca");
    private ProductFactory productFactory;
    private ProductDto productDTO;

    @Before
    public void setUp() {
        productDTO = new ProductDto();
        productDTO.title = "title";
        productDTO.description = "description";
        productDTO.suggestedPrice = 4.29;
        productDTO.categories = List.of("beauty");

        productFactory = new ProductFactory();
    }

    private void checkForNull(Product product) {
        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isNull();
        assertThat(product.getTitle()).isNotNull();
        assertThat(product.getDescription()).isNotNull();
        assertThat(product.getSuggestedPrice()).isNotNull();
        assertThat(product.getCategories()).isNotNull();
        assertThat(product.getSellerId()).isNotNull();
    }

    @Test
    public void canCreateProductNormal() {
        var product = productFactory.createProduct(productDTO, SELLER_ID);
        checkForNull(product);
    }

    @Test
    public void canCreateProductWithMultipleCategories() {
        productDTO.categories = List.of("beauty", "test");
        var expectedCategories = List.of(ProductCategory.BEAUTY, ProductCategory.OTHER);

        var product = productFactory.createProduct(productDTO, SELLER_ID);

        checkForNull(product);
        assertThat(product.getCategories()).isEqualTo(expectedCategories);
    }

    @Test
    public void canCreateProductWithNoCategoryAsEmpty() {
        productDTO.categories = List.of();

        var product = productFactory.createProduct(productDTO, SELLER_ID);

        checkForNull(product);
        assertThat(product.getCategories()).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowOnCategoriesNull() {
        productDTO.categories = null;

        productFactory.createProduct(productDTO, SELLER_ID);
    }
}