package ulaval.glo2003;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ProductCategoryTest {

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