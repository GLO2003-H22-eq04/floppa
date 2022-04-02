package ulaval.glo2003;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ulaval.glo2003.domain.seller.Seller;
import ulaval.glo2003.domain.seller.repository.SellerListRepository;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.util.Optional;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SellerRepositoryTests {

    private static final UUID NON_EXISTENT_SELLER_ID = UUID.fromString("df1a611f-00c8-47d5-b4d4-7276cd4165e2");
    private static final UUID EXPECTED_SELLER_ID = UUID.fromString("9da43ed6-f5be-47ae-a3c9-7d5ab63d5f4a");

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

        assertThat(sellerListRepository.findById(id)).isEqualTo(Optional.of(sellerMock1));
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
    public void shouldNotFindNonExistentId() {
        var product = sellerListRepository.findById(NON_EXISTENT_SELLER_ID);

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
        var result = sellerListRepository.existById(NON_EXISTENT_SELLER_ID);

        assertThat(result).isFalse();
    }
}
