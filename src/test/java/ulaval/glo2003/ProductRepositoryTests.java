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
    private Product productMock1;

    @Mock
    private Product productMock2;

    private ProductRepository productListRepository;

    @Before
    public void before() {
        productListRepository = new ProductListRepository();
    }

    @Test
    public void canAddOneProductNormal() {
        var id = productListRepository.add(productMock1);

        assertThat(id).isEqualTo(0);
    }

    @Test
    public void canAddTwoProductNormal() {
        var id1 = productListRepository.add(productMock1);
        var id2 = productListRepository.add(productMock2);

        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    public void canFindByIdNormal() {
        var id = productListRepository.add(productMock1);
        var product = productListRepository.findById(id);

        assertThat(product.isPresent()).isTrue();
        assertThat(product.get().getProductId()).isEqualTo(id);
    }

    @Test
    public void shouldNotFindInvalidId() {
        var product = productListRepository.findById(0);

        assertThat(product.isPresent()).isFalse();
    }

    @Test
    public void canFindAllNormal() {
        productListRepository.add(productMock1);
        productListRepository.add(productMock2);

        var productList = productListRepository.findAll();

        assertThat(productList.isEmpty()).isFalse();
        assertThat(productList.size() == 2).isTrue();
        assertThat(productList.contains(productMock1)).isTrue();
        assertThat(productList.contains(productMock2)).isTrue();
    }
}
