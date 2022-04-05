package ulaval.glo2003.domain.product.repository;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.Main;
import ulaval.glo2003.domain.config.DatastoreFactory;
import ulaval.glo2003.domain.offer.OfferItem;
import ulaval.glo2003.domain.offer.Offers;
import ulaval.glo2003.domain.product.Amount;
import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.ProductCategory;
import ulaval.glo2003.domain.product.repository.ProductMongodbRepository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;

public class ProductMongodbRepositoryTests {
    private static final UUID NOT_PRESENT_ID = UUID.fromString("694bb404-95c1-48c4-9874-a937f42be2e6");

    private Product product1;
    private Product product2;

    private Offers offers;
    private OfferItem offerItem;

    private ProductMongodbRepository productMongodbRepository;

    @Before
    public void before() {
        productMongodbRepository = new ProductMongodbRepository(new DatastoreFactory(Main.loadConfig()));

        offerItem = new OfferItem(OffsetDateTime.now().minusDays(3));
        offerItem.setAmount(new Amount(11));
        offerItem.setEmail("email@me.com");
        offerItem.setMessage("message");
        offerItem.setName("Nom");
        offerItem.setCreatedAt(OffsetDateTime.now().minusDays(1));
        offerItem.setPhoneNumber("4186666666");

        var offerslist = new ArrayList<OfferItem>();
        offerslist.add(offerItem);

        offers = new Offers(offerslist);

        product1 = new Product(Instant.now().atOffset(ZoneOffset.UTC));
        product1.setTitle("titleDT0");
        product1.setDescription("descriptionDT01");
        product1.setSuggestedPrice(new Amount(4.29));
        product1.addCategory(ProductCategory.BEAUTY);
        product1.addCategory(ProductCategory.OTHER);
        product1.setOffers(offers);


        product2 = new Product(Instant.now().atOffset(ZoneOffset.UTC));
        product2.setTitle("titleDT02");
        product2.setDescription("descriptionDT02");
        product2.setSuggestedPrice(new Amount(6.49));
        product2.addCategory(ProductCategory.SPORTS);
        product2.addCategory(ProductCategory.OTHER);

        new DatastoreFactory(Main.loadConfig()).getDatastore().getDatabase().drop();
    }

    @Test
    public void canAddOneProductNormal() {
        var id = productMongodbRepository.add(product1);
        List<Product> all = productMongodbRepository.findAll();
        assertThat(all.stream().findFirst().get().equals(product1)).isTrue();
    }

    @Test
    public void canAddTwoProductNormal() {
        var id1 = productMongodbRepository.add(product1);
        var id2 = productMongodbRepository.add(product2);

        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    public void canFindByIdNormal() {
        var id = productMongodbRepository.add(product1);

        var product = productMongodbRepository.findById(id);

        assertThat(product.isPresent()).isTrue();
        assertThat(product.get().getTitle()).isEqualTo(product1.getTitle());
    }

    @Test
    public void shouldNotFindInvalidId() {
        var product = productMongodbRepository.findById(NOT_PRESENT_ID);

        assertThat(product.isPresent()).isFalse();
    }

    @Test
    public void canFindAllNormal() {
        productMongodbRepository.add(product1);
        productMongodbRepository.add(product2);

        var productList = productMongodbRepository.findAll();

        assertThat(productList.size()).isEqualTo(2);
        assertThat(productList.contains(product1)).isTrue();
        assertThat(productList.contains(product2)).isTrue();
    }
}
