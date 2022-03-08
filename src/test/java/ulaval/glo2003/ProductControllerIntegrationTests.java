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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerIntegrationTests extends JerseyTest {

    private final static String VALID_ID = "0";
    private final static String INVALID_ID = "42";
    private final static int MISSING_ID = 33;

    @Mock
    private SellerRepository sellerListRepositoryMock;

    @Mock
    private ProductRepository productListRepositoryMock;

    @Mock
    private ProductFactory productFactoryMock;

    private ProductDTO productDTO1;
    private ProductDTO productDTO2;
    private Product product;

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

        product = new Product();
        product.setProductId(0);
        product.setDescription("Un produit");
        product.setTitle("Produit");
        product.setSellerId("0");
        product.setSuggestedPrice(new Amount(5.01));

        var sellerDTO = new SellerDTO();
        sellerDTO.name = "John Doe";
        sellerDTO.bio = "Un seller";
        sellerDTO.birthDate = LocalDate.now().minusYears(20);

        var seller = new Seller(sellerDTO);

        when(sellerListRepositoryMock.existById(Integer.parseInt(VALID_ID))).thenReturn(true);
        when(sellerListRepositoryMock.existById(Integer.parseInt(INVALID_ID))).thenReturn(false);

        when(sellerListRepositoryMock.findById(Integer.parseInt(VALID_ID))).thenReturn(Optional.of(seller));
        when(productListRepositoryMock.findById(Integer.parseInt(VALID_ID))).thenReturn(Optional.of(product));
        when(productListRepositoryMock.findById(MISSING_ID)).thenReturn(Optional.empty());
    }

    @Override
    protected Application configure() {
        var resourceConfig = Main.getRessourceConfig();
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(sellerListRepositoryMock).to(SellerRepository.class).ranked(2);
                bind(productListRepositoryMock).to(ProductRepository.class).ranked(2);
                bind(productFactoryMock).to(ProductFactory.class).ranked(2);
            }
        });
        return resourceConfig;
    }

    @Test
    public void canReceiveUrlOfCreatedProduct() {
        var response = postCreatingProductResponse(productDTO1, VALID_ID);

        var locationHeader = (String) response.getHeaders().getFirst("Location");

        assertThat(locationHeader.contains(VALID_ID)).isTrue();
        assertThat(IntegrationUtils.isUrl(locationHeader)).isTrue();
    }

    @Test
    public void canRejectRequestOnNullProduct() {
        var response = postCreatingProductResponse(null, VALID_ID);

        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnUnknownSellerId() {
        var response = postCreatingProductResponse(productDTO1, INVALID_ID);

        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidFormatSellerId() {
        var response = postCreatingProductResponse(productDTO1, "test");

        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidPrice() {
        productDTO2.suggestedPrice = -1;

        var response = postCreatingProductResponse(productDTO2, VALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidTitle() {
        productDTO2.title = "";

        var response = postCreatingProductResponse(productDTO2, VALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnNullTitle() {
        productDTO2.title = null;

        var response = postCreatingProductResponse(productDTO2, VALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidDescription() {
        productDTO2.description = "";

        var response = postCreatingProductResponse(productDTO2, VALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnNullDescription() {
        productDTO2.description = null;

        var response = postCreatingProductResponse(productDTO2, VALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canGetExistingProduct(){
        var response = getProductResponse(Integer.parseInt(VALID_ID));

        var entity = response.readEntity(ProductInfoResponseDTO.class);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(entity.id).isEqualTo(String.valueOf(product.getProductId()));
        assertThat(entity.title).isEqualTo(product.getTitle());
        assertThat(entity.categories).isEqualTo(product.getCategories());
        assertThat(entity.createdAt).isEqualTo(product.getCreatedAt());
        assertThat(entity.suggestedPrice.getValue()).isEqualTo(product.getSuggestedPrice().getValue());
        assertThat(entity.description).isEqualTo(product.getDescription());
        assertThat(entity.seller.id).isEqualTo(String.valueOf(product.getSellerId()));
    }

    @Test
    public void shouldRejectNonExistingProduct() {
        var response = getProductResponse(MISSING_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    private Response postCreatingProductResponse(ProductDTO productDTO, String sellerId) {
        return target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, String.valueOf(sellerId)).post(Entity.entity(productDTO, MediaType.APPLICATION_JSON_TYPE));
    }

    private Response getProductResponse(int productId) {
        return target(ProductController.PRODUCTS_PATH + '/' + productId).request().get();
    }
}