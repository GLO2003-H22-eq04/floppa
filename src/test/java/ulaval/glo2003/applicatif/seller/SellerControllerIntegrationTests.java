package ulaval.glo2003.applicatif.seller;

import com.google.common.truth.Truth;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ulaval.glo2003.IntegrationUtils;
import ulaval.glo2003.Main;
import ulaval.glo2003.api.seller.SellerController;
import ulaval.glo2003.applicatif.offer.OfferItemResponseDto;
import ulaval.glo2003.applicatif.seller.CurrentSellerDto;
import ulaval.glo2003.applicatif.seller.SellerDto;
import ulaval.glo2003.applicatif.seller.SellerInfoResponseDto;
import ulaval.glo2003.domain.offer.OfferItem;
import ulaval.glo2003.domain.offer.Offers;
import ulaval.glo2003.domain.product.Amount;
import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.repository.ProductRepository;
import ulaval.glo2003.domain.seller.Seller;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SellerControllerIntegrationTests extends JerseyTest {

    private final static UUID VALID_SELLER_ID = UUID.fromString("34272b55-6b32-477c-8ab8-1f4d09a66949");
    private final static UUID INVALID_SELLER_ID = UUID.fromString("5a409ca2-7ec8-4d89-a224-0f6894056a42");

    private final static UUID VALID_PRODUCT_ID = UUID.fromString("e372e035-1072-485a-9fbc-473fd9017658");

    @Mock
    private SellerRepository sellerListRepositoryMock;

    @Mock
    private ProductRepository productRepositoryMock;

    private SellerDto aSellerDTO1;
    private SellerDto aSellerDTO2;

    private Seller seller;

    private Product product;

    private OfferItemResponseDto offerItemResponseDto;

    private Offers offer;
    private OfferItem offerItem;

    @Before
    public void before() {
        aSellerDTO1 = new SellerDto();
        aSellerDTO1.name = "Testnom";
        aSellerDTO1.bio = "wow";
        aSellerDTO1.birthDate = LocalDate.now().minusYears(20);

        aSellerDTO2 = new SellerDto();
        aSellerDTO2.name = "Coolnom";
        aSellerDTO2.bio = "amazing";
        aSellerDTO2.birthDate = LocalDate.now().minusYears(30);

        seller = new Seller(aSellerDTO1);
        seller.setId(VALID_SELLER_ID);


        product = new Product(Instant.now().atOffset(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));
        product.setProductId(VALID_PRODUCT_ID);
        product.setDescription("Unproduit");
        product.setSellerId(VALID_SELLER_ID);
        product.setTitle("Produit");
        product.setSuggestedPrice(new Amount(49.01));
        seller.getProducts().add(product);

        offerItem = new OfferItem(Instant.now().atOffset(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));
        offerItem.setName("Buyer");
        offerItem.setMessage("UneOffre");
        offerItem.setEmail("u.n@email.com");
        offerItem.setPhoneNumber("18191234567");
        offerItem.setAmount(new Amount(5.01));
        List<OfferItemResponseDto> items = new ArrayList<>();
        items.add(offerItemResponseDto);
        offer = new Offers();
        offer.getListOffer().add(offerItem);
        product.getOffers().addNewOffer(offerItem);


        when(sellerListRepositoryMock.findById(VALID_SELLER_ID)).thenReturn(Optional.of(seller));
        when(sellerListRepositoryMock.add(any())).thenReturn(VALID_SELLER_ID);

        when(productRepositoryMock.productOf(VALID_SELLER_ID)).thenReturn(new ArrayList<>());
    }

    @Override
    protected Application configure() {
        var resourceConfig = Main.getRessourceConfig(Main.loadConfig());
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(sellerListRepositoryMock).to(SellerRepository.class).ranked(2);
                bind(productRepositoryMock).to(ProductRepository.class).ranked(2);
            }
        });
        return resourceConfig;
    }

    @Test
    public void canReceiveUrlOfCreatedSeller() {
        var response = postCreatingSellerResponse(aSellerDTO1);

        var locationHeader = (String) response.getHeaders().getFirst("Location");

        assertThat(locationHeader.contains(VALID_SELLER_ID.toString())).isTrue();
        Truth.assertThat(IntegrationUtils.isUrl(locationHeader)).isTrue();
    }

    @Test
    public void canCreateMultipleSellers() {
        var response1 = postCreatingSellerResponse(aSellerDTO1);
        var response2 = postCreatingSellerResponse(aSellerDTO2);

        var locationHeader1 = (String) response1.getHeaders().getFirst("Location");
        var locationHeader2 = (String) response2.getHeaders().getFirst("Location");

        assertThat(IntegrationUtils.isUrl(locationHeader1)).isTrue();
        assertThat(IntegrationUtils.isUrl(locationHeader2)).isTrue();
    }

    @Test
    public void canReceiveRequestGetSellerNormal() {
        var response = getSellerResponse(VALID_SELLER_ID);
        var status = response.getStatus();
        var entity = response.readEntity(SellerInfoResponseDto.class);

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(entity.name).isEqualTo(seller.getName());
        assertThat(entity.bio).isEqualTo(seller.getBio());
        assertThat(entity.createdAt).isEqualTo(seller.getCreatedAt());
    }

    @Test
    public void canRejectRequestGetSellerInvalidId() {
        var response = getSellerResponse(INVALID_SELLER_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void canReceiveRequestGetCurrentSellerNormal() {
        var response = getCurrentSellerResponse(VALID_SELLER_ID);
        var status = response.getStatus();
        var entity = response.readEntity(CurrentSellerDto.class);

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(entity.id).isEqualTo(seller.getId());
        assertThat(entity.name).isEqualTo(seller.getName());
        assertThat(entity.bio).isEqualTo(seller.getBio());
        assertThat(entity.birthdate).isEqualTo(seller.getBirthDate());
        assertThat(entity.products.get(0).id).isEqualTo(product.getProductId());
        assertThat(entity.products.get(0).title).isEqualTo(product.getTitle());
        assertThat(entity.products.get(0).description).isEqualTo(product.getDescription());
        assertThat(entity.products.get(0).createdAt).isEqualTo(product.getCreatedAt());
        assertThat(entity.products.get(0).suggestedPrice).isEqualTo(product.getSuggestedPrice().getValue());
        assertThat(entity.products.get(0).categories).isEqualTo(product.getCategories());
        assertThat(entity.products.get(0).offers.min).isEqualTo(offer.getMin());
        assertThat(entity.products.get(0).offers.max).isEqualTo(offer.getMax());
        assertThat(entity.products.get(0).offers.mean).isEqualTo(offer.getMean());
        assertThat(entity.products.get(0).offers.count).isEqualTo(offer.getCount());
        assertThat(entity.products.get(0).offers.items.get(0).id).isEqualTo(offer.getListOffer().get(0).getOfferId());
        assertThat(entity.products.get(0).offers.items.get(0).createdAt).isEqualTo(
                offer.getListOffer().get(0).getCreatedAt());
        assertThat(entity.products.get(0).offers.items.get(0).amount).isEqualTo(
                offer.getListOffer().get(0).getAmount().getValue());
        assertThat(entity.products.get(0).offers.items.get(0).message).isEqualTo(
                offer.getListOffer().get(0).getMessage());
        assertThat(entity.products.get(0).offers.items.get(0).buyer.name).isEqualTo(
                offer.getListOffer().get(0).getName());
        assertThat(entity.products.get(0).offers.items.get(0).buyer.email).isEqualTo(
                offer.getListOffer().get(0).getEmail());
        assertThat(entity.products.get(0).offers.items.get(0).buyer.phoneNumber).isEqualTo(
                offer.getListOffer().get(0).getPhoneNumber());
    }

    @Test
    public void canRejectRequestGetCurrentSellerInvalidId() {
        var response = getCurrentSellerResponse(INVALID_SELLER_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    private Response postCreatingSellerResponse(SellerDto sellerDTO) {
        return target(SellerController.SELLERS_PATH).request().post(Entity.entity(sellerDTO, MediaType.APPLICATION_JSON_TYPE));
    }

    private Response getSellerResponse(UUID sellerId) {
        return target(SellerController.SELLERS_PATH + '/' + sellerId).request().get();
    }

    private Response getCurrentSellerResponse(UUID sellerId) {
        return target(SellerController.SELLERS_PATH + "/@me").request().header(SellerController.SELLER_ID_HEADER, sellerId).get();
    }
}