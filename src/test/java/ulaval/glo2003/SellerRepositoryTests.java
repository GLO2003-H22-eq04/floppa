package ulaval.glo2003;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SellerRepositoryTests {

    private static final int INVALID_SELLER_ID = 42;
    private static final int EXPECTED_SELLER_ID = 0;

    @Mock
    private Seller sellerMock1;

    @Mock
    private Seller sellerMock2;

    private SellerRepository sellerListRepository;

    @Before
    public void before() {
        sellerListRepository = new SellerListRepository();
    }

    @Test
    public void canAddOneSellerNormal() {
        var id = sellerListRepository.add(sellerMock1);

        assertThat(id).isEqualTo(EXPECTED_SELLER_ID);
    }

    @Test
    public void canAddTwoSellerNormal() {
        var id1 = sellerListRepository.add(sellerMock1);
        var id2 = sellerListRepository.add(sellerMock2);

        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    public void canFindByIdNormal() {
        var id = sellerListRepository.add(sellerMock1);

        var seller = sellerListRepository.findById(id);

        assertThat(seller.isPresent()).isTrue();
    }

    @Test
    public void shouldNotFindInvalidId() {
        var product = sellerListRepository.findById(INVALID_SELLER_ID);

        assertThat(product.isPresent()).isFalse();
    }

    @Test
    public void canCheckIfExistIdNormal() {
        var id = sellerListRepository.add(sellerMock1);

        var result = sellerListRepository.existById(id);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldFailCheckIfExistInvalidId() {
        var result = sellerListRepository.existById(INVALID_SELLER_ID);

        assertThat(result).isFalse();
    }
}
