package ulaval.glo2003.domain.product.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import ulaval.glo2003.domain.product.Amount;
import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.ProductCategory;
import ulaval.glo2003.domain.product.repository.ProductListRepository;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ProductListRepositoryTests {
    private static final UUID NOT_PRESENT_ID = UUID.fromString("694bb404-95c1-48c4-9874-a937f42be2e6");

    private Product product1;
    private Product product2;

    private ProductListRepository productListRepository;

    @Before
    public void before() {
        productListRepository = new ProductListRepository();
        product1 = new Product(Instant.now().atOffset(ZoneOffset.UTC));
        product1.setTitle("titleDT01");
        product1.setDescription("descriptionDT01");
        product1.setSuggestedPrice(new Amount(4.29));
        product1.addCategory(ProductCategory.BEAUTY);
        product1.addCategory(ProductCategory.OTHER);


        product2 = new Product(Instant.now().atOffset(ZoneOffset.UTC));
        product2.setTitle("titleDT02");
        product2.setDescription("descriptionDT02");
        product2.setSuggestedPrice(new Amount(6.49));
        product2.addCategory(ProductCategory.SPORTS);
        product2.addCategory(ProductCategory.OTHER);

    }

    @Test
    public void canAddOneProductNormal() {
        var id = productListRepository.add(product1);
        assertThat(productListRepository.findAll()).contains(product1);
    }

    @Test
    public void canAddTwoProductNormal() {
        var id1 = productListRepository.add(product1);
        var id2 = productListRepository.add(product2);

        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    public void canFindByIdNormal() {
        var id = productListRepository.add(product1);

        var product = productListRepository.findById(id);

        assertThat(product.isPresent()).isTrue();
        assertThat(product.get().getTitle()).isEqualTo(product1.getTitle());
    }

    @Test
    public void shouldNotFindInvalidId() {
        var product = productListRepository.findById(NOT_PRESENT_ID);

        assertThat(product.isPresent()).isFalse();
    }

    @Test
    public void canFindAllNormal() {
        productListRepository.add(product1);
        productListRepository.add(product2);

        var productList = productListRepository.findAll();

        assertThat(productList.size()).isEqualTo(2);
        assertThat(productList.contains(product1)).isTrue();
        assertThat(productList.contains(product2)).isTrue();
    }
}
