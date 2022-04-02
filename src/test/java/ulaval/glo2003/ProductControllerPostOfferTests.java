package ulaval.glo2003;

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
import ulaval.glo2003.api.product.ProductController;
import ulaval.glo2003.applicatif.offer.OfferItemDto;
import ulaval.glo2003.domain.offer.OfferItem;
import ulaval.glo2003.domain.product.Amount;
import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerPostOfferTests extends JerseyTest {


    private final static UUID VALID_PRODUCT_ID = UUID.fromString("e372e035-1072-485a-9fbc-473fd9017658");
    private final static UUID VALID_SELLER_ID = UUID.fromString("2f3ca1d6-1625-4de4-b08c-5f3804b254c9");
    private final static UUID MISSING_ID = UUID.fromString("93dad72a-838e-4623-8afa-47a089cac44c");

    @Mock
    private ProductRepository productListRepositoryMock;

    private OfferItemDto offerItemDto;

    @Before
    public void before() {
        var product = new Product();
        product.setProductId(VALID_PRODUCT_ID);
        product.setDescription("Un produit");
        product.setTitle("Produit");
        product.setSellerId(VALID_SELLER_ID);
        product.setSuggestedPrice(new Amount(5.01));

        offerItemDto = new OfferItemDto();
        offerItemDto.name = "John Doe";
        offerItemDto.amount = 10;
        offerItemDto.message = "Donec porttitor interdum lacus sed finibus. Nam pulvinar facilisis posuere. Maecenas vel lorem amet.";
        offerItemDto.email = "john.doe@gmail.com";
        offerItemDto.phoneNumber = "18191234567";

        var offerItem = new OfferItem();
        offerItem.setName(offerItemDto.name);
        offerItem.setMessage(offerItemDto.message);
        offerItem.setAmount(new Amount(offerItemDto.amount));
        offerItem.setEmail(offerItemDto.email);
        offerItem.setPhoneNumber(offerItemDto.phoneNumber);

        when(productListRepositoryMock.findById(VALID_PRODUCT_ID)).thenReturn(Optional.of(product));
        when(productListRepositoryMock.findById(MISSING_ID)).thenReturn(Optional.empty());
    }

    @Override
    protected Application configure() {
        var resourceConfig = Main.getRessourceConfig();
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(productListRepositoryMock).to(ProductRepository.class).ranked(2);
            }
        });
        return resourceConfig;
    }

    @Test
    public void canAcceptRequestOnValidOffer(){
        var response = postCreatingProductResponse(offerItemDto, VALID_PRODUCT_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidProductId(){
        var response = postCreatingProductResponse(offerItemDto, MISSING_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidName(){
        offerItemDto.name = "";
        var response = postCreatingProductResponse(offerItemDto, VALID_PRODUCT_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidEmail(){
        offerItemDto.email = "bad email";
        var response = postCreatingProductResponse(offerItemDto, VALID_PRODUCT_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidPhoneNumber(){
        offerItemDto.email = "1";
        var response = postCreatingProductResponse(offerItemDto, VALID_PRODUCT_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnTooLowAmount(){
        offerItemDto.amount = 1;
        var response = postCreatingProductResponse(offerItemDto, VALID_PRODUCT_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnTooShortMessage(){
        offerItemDto.email = "Too Short";
        var response = postCreatingProductResponse(offerItemDto, VALID_PRODUCT_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnNullName(){
        offerItemDto.name = null;
        var response = postCreatingProductResponse(offerItemDto, VALID_PRODUCT_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnNullEmail(){
        offerItemDto.email = null;
        var response = postCreatingProductResponse(offerItemDto, VALID_PRODUCT_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnNullPhoneNumber(){
        offerItemDto.email = null;
        var response = postCreatingProductResponse(offerItemDto, VALID_PRODUCT_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnNullMessage(){
        offerItemDto.email = null;
        var response = postCreatingProductResponse(offerItemDto, VALID_PRODUCT_ID.toString());
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    private Response postCreatingProductResponse(OfferItemDto offerItemDto, String productId) {
        return target(ProductController.PRODUCTS_PATH + "/" + productId + "/offers").request().post(Entity.entity(offerItemDto, MediaType.APPLICATION_JSON_TYPE));
    }
}
