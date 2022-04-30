package ulaval.glo2003.domain.product;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ProductCategoryTests {

    @Test
    public void canTransformExistingCategoryNormal() {
        var category = "beauty";

        var productCategory = ProductCategory.findByName(category);

        assertThat(productCategory).isEqualTo(ProductCategory.beauty);
    }

    @Test
    public void canTransformOtherCategoryNormal() {
        var category = "test";

        var productCategory = ProductCategory.findByName(category);

        assertThat(productCategory).isEqualTo(ProductCategory.other);
    }
}