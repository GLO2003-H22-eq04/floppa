package ulaval.glo2003;

import org.junit.Test;
import ulaval.glo2003.domain.product.ProductCategory;

import static com.google.common.truth.Truth.assertThat;

public class ProductCategoryTests {

    @Test
    public void canTransformExistingCategoryNormal() {
        var category = "beauty";

        var productCategory = ProductCategory.findByName(category);

        assertThat(productCategory).isEqualTo(ProductCategory.BEAUTY);
    }

    @Test
    public void canTransformOtherCategoryNormal() {
        var category = "test";

        var productCategory = ProductCategory.findByName(category);

        assertThat(productCategory).isEqualTo(ProductCategory.OTHER);
    }
}