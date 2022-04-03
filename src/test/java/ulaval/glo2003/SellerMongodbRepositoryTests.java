package ulaval.glo2003;

import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.domain.config.DatastoreFactory;
import ulaval.glo2003.domain.seller.Seller;
import ulaval.glo2003.domain.seller.repository.SellerMongodbRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;

public class SellerMongodbRepositoryTests {
    private static final UUID NON_EXISTENT_SELLER_ID = UUID.fromString("df1a611f-00c8-47d5-b4d4-7276cd4165e2");
    private static final UUID EXPECTED_SELLER_ID_1 = UUID.fromString("9da43ed6-f5be-47ae-a3c9-7d5ab63d5f4a");
    private static final UUID EXPECTED_SELLER_ID_2 = UUID.fromString("49e7e0f2-1aca-48bc-80db-c7dce243b132");

    private Seller seller1;
    private Seller seller2;

    private SellerMongodbRepository sellerListRepository;

    @Before
    public void before() {
        new DatastoreFactory(Main.loadConfig()).getDatastore().getDatabase().drop();

        sellerListRepository = new SellerMongodbRepository(new DatastoreFactory(Main.loadConfig()));

        seller1 = new Seller(EXPECTED_SELLER_ID_1, "Nom1", "bio1", LocalDate.now().minusYears(30), OffsetDateTime.now().minusDays(2));

        seller2 = new Seller(EXPECTED_SELLER_ID_2, "Nom2", "bio2", LocalDate.now().minusYears(40), OffsetDateTime.now().minusDays(3));

    }

    @Test
    public void canAddOneSellerNormal() {
        var id = sellerListRepository.add(seller1);

        assertThat(sellerListRepository.findById(id).get()).isEqualTo(seller1);
    }

    @Test
    public void canAddTwoSellerNormal() {
        var id1 = sellerListRepository.add(seller1);
        var id2 = sellerListRepository.add(seller2);

        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    public void canFindByIdNormal() {
        var id = sellerListRepository.add(seller1);

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
        var id = sellerListRepository.add(seller1);

        var result = sellerListRepository.existById(id);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldFailCheckIfExistInvalidId() {
        var result = sellerListRepository.existById(NON_EXISTENT_SELLER_ID);

        assertThat(result).isFalse();
    }
}
