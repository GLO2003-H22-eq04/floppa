package ulaval.glo2003;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ProductFactoryTests {

    private static final String SELLER_ID = "SELLER-ID";
    private ProductFactory productFactory;
    private ProductDTO productDTO;

    @Before
    public void setUp() {
        productDTO = new ProductDTO();
        productDTO.title = "title";
        productDTO.description = "description";
        productDTO.suggestedPrice = 4.29;
        productDTO.categories = List.of("beauty");

        productFactory = new ProductFactory();
    }

    private void checkForNull(Product product) {
        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isNotNull();
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