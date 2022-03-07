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

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerIntegrationTests extends JerseyTest {

    private final static int VALID_ID = 0;
    private final static int INVALID_ID = 42;

    @Mock
    private SellerRepository sellerListRepositoryMock;

    @Mock
    private ProductRepository productListRepositoryMock;

    @Mock
    private ProductFactory productFactoryMock;

    private ProductDTO productDTO1;
    private ProductDTO productDTO2;

    @Before
    public void before() {
        productDTO1 = new ProductDTO();
        productDTO1.title = "titleDT01";
        productDTO1.description = "descriptionDT01";
        productDTO1.suggestedPrice = 4.29;
        productDTO1.categories = List.of("beauty");

        productDTO2 = new ProductDTO();
        productDTO2.title = "titleDT02";
        productDTO2.description = "descriptionDT02";
        productDTO2.suggestedPrice = 6.49;
        productDTO2.categories = List.of("sports");

        when(sellerListRepositoryMock.existById(VALID_ID)).thenReturn(true);
        when(sellerListRepositoryMock.existById(INVALID_ID)).thenReturn(false);
    }

    @Override
    protected Application configure() {
        var ressourceConfig = Main.getRessourceConfig();
        ressourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(sellerListRepositoryMock).to(SellerRepository.class).ranked(2);
                bind(productListRepositoryMock).to(ProductRepository.class).ranked(2);
                bind(productFactoryMock).to(ProductFactory.class).ranked(2);
            }
        });
        return ressourceConfig;
    }

    @Test
    public void canReceiveUrlOfCreatedProduct() {
        var response = getResponse(productDTO1, VALID_ID);

        var locationHeader = (String) response.getHeaders().getFirst("Location");

        assertThat(locationHeader.contains(String.valueOf(VALID_ID))).isTrue();
        assertThat(IntegrationUtils.isUrl(locationHeader)).isTrue();
    }

    @Test
    public void canRejectRequestOnNullProduct() {
        var response = getResponse(null, VALID_ID);

        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnUnknownSellerId() {
        var response = getResponse(productDTO1, INVALID_ID);

        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidFormatSellerId() {
        var response = target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER,"test").post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));

        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidPrice() {
        productDTO2.suggestedPrice = -1;

        var response = getResponse(productDTO2, VALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidTitle() {
        productDTO2.title = "";

        var response = getResponse(productDTO2, VALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnNullTitle() {
        productDTO2.title = null;

        var response = getResponse(productDTO2, VALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidDescription() {
        productDTO2.description = "";

        var response = getResponse(productDTO2, VALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnNullDescription() {
        productDTO2.description = null;

        var response = getResponse(productDTO2, VALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    private Response getResponse(ProductDTO productDTO, int sellerId) {
        return target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, String.valueOf(sellerId)).post(Entity.entity(productDTO, MediaType.APPLICATION_JSON_TYPE));
    }
}