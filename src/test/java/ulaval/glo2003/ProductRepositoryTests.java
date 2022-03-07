package ulaval.glo2003;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ProductRepositoryTests {

    @Mock
    private Product product;

    private ProductRepository productRepository;

    @Before
    public void before() {
        productRepository = new ProductListRepository();
    }

    @Test
    public void canAddOneProductNormal() {
        var id = productRepository.add(product);

        assertThat(id).isEqualTo(0);
    }

    @Test
    public void canAddTwoProductNormal() {
        var id1 = productRepository.add(product);
        var id2 = productRepository.add(product);

        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    public void canFindByIdNormal() {
        var id = productRepository.add(product);
        var product = productRepository.findById(id);

        assertThat(product.isPresent()).isTrue();
        assertThat(product.get().getProductId()).isEqualTo(id);
    }

    @Test
    public void shouldNotFindInvalidId() {
        var product = productRepository.findById(0);

        assertThat(product.isPresent()).isFalse();
    }
}
