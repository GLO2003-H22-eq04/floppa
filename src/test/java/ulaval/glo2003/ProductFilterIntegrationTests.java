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
import ulaval.glo2003.applicatif.seller.SellerDto;
import ulaval.glo2003.domain.product.Amount;
import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.ProductCategory;
import ulaval.glo2003.domain.product.repository.ProductRepository;
import ulaval.glo2003.domain.seller.Seller;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductFilterIntegrationTests extends JerseyTest {

    private final static UUID VALID_SELLER_ID_1 = UUID.fromString("a9b09f0e-1d94-46c7-80a3-06640d6f1025");
    private final static UUID VALID_SELLER_ID_2 = UUID.fromString("6d3a24a8-154b-427d-b5a4-296bd79c90a4");
    private final static UUID VALID_ID2 = UUID.fromString("bba621cf-8184-4cc2-a669-4bd40a05beb8");

    private Product product1;
    private Product product2;
    private SellerDto sellerDTO1;
    private SellerDto sellerDTO2;

    private Seller seller1;
    private Seller seller2;

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private SellerRepository sellerRepositoryMock;

    @Before
    public void before() {
        product1 = new Product(Instant.now().atOffset(ZoneOffset.UTC));
        product1.setTitle("titleDT01");
        product1.setDescription("descriptionDT01");
        product1.setSuggestedPrice(new Amount(4.29));
        product1.addCategory(ProductCategory.BEAUTY);
        product1.addCategory(ProductCategory.OTHER);
        product1.setSellerId(VALID_SELLER_ID_1);

        product2 = new Product(Instant.now().atOffset(ZoneOffset.UTC));
        product2.setTitle("titleDT02");
        product2.setDescription("descriptionDT02");
        product2.setSuggestedPrice(new Amount(6.49));
        product2.addCategory(ProductCategory.SPORTS);
        product2.addCategory(ProductCategory.OTHER);
        product2.setSellerId(VALID_SELLER_ID_2);

        sellerDTO1 = new SellerDto();
        sellerDTO1.name = "John Doe";
        sellerDTO1.bio = "Un seller";
        sellerDTO1.birthDate = LocalDate.now().minusYears(20);

        sellerDTO2 = new SellerDto();
        sellerDTO2.name = "John Moe";
        sellerDTO2.bio = "Un seller";
        sellerDTO2.birthDate = LocalDate.now().minusYears(30);


        seller1 = new Seller(sellerDTO1);
        seller2 = new Seller(sellerDTO2);

        var products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);
        when(productRepositoryMock.findAll()).thenReturn(products);

        when(sellerRepositoryMock.findById(VALID_SELLER_ID_1)).thenReturn(Optional.of(seller1));
        when(sellerRepositoryMock.findById(VALID_SELLER_ID_2)).thenReturn(Optional.of(seller2));
    }

    @Override
    protected Application configure() {
        return Main.getRessourceConfig(Main.loadConfig()).register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(productRepositoryMock).to(ProductRepository.class).ranked(2);
                bind(sellerRepositoryMock).to(SellerRepository.class).ranked(2);
            }
        });
    }


    @Test
    public void canRequestWithoutArgs() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_SELLER_ID_1).post(Entity.entity(product1, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("", "");

        assertThat(responseGET.readEntity(String.class)).contains(product1.getTitle());
    }

    @Test
    public void canRequestWithFilterSingleResultFromTitle() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var responseGET = getProductResponse("title", "titleDT01").readEntity(String.class);

        assertThat(responseGET).contains(product1.getTitle());
        assertThat(responseGET).doesNotContain(product2.getTitle());
    }

    @Test
    public void canRequestWithFilterManyResultFromTitle() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var responseGET = getProductResponse("title", "DT0").readEntity(String.class);

        assertThat(responseGET).contains(product1.getTitle());
        assertThat(responseGET).contains(product2.getTitle());
    }

    @Test
    public void canRequestWithFilterSingleResultFromMaxPrice() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var responseGET = getProductResponse("maxPrice", 5).readEntity(String.class);

        assertThat(responseGET).contains(product1.getTitle());
        assertThat(responseGET).doesNotContain(product2.getTitle());
    }

    @Test
    public void canRequestWithFilterMultipleResultFromMaxPrice() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var responseGET = getProductResponse("maxPrice", 10).readEntity(String.class);

        assertThat(responseGET).contains(product1.getTitle());
        assertThat(responseGET).contains(product2.getTitle());
    }


    @Test
    public void canRequestWithFilterSingleResultFromMinPrice() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var responseGET = getProductResponse("minPrice", 5).readEntity(String.class);

        assertThat(responseGET).doesNotContain(product1.getTitle());
        assertThat(responseGET).contains(product2.getTitle());
    }

    @Test
    public void canRequestWithFilterMultipleResultFromMinPrice() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var responseGET = getProductResponse("minPrice", 1).readEntity(String.class);

        assertThat(responseGET).contains(product1.getTitle());
        assertThat(responseGET).contains(product2.getTitle());
    }

    @Test
    public void canRequestWithFilterSingleResultFromSellerID() {
        var responseGET = getProductResponse("sellerId", VALID_SELLER_ID_1).readEntity(String.class);

        assertThat(responseGET).contains(product1.getTitle());
        assertThat(responseGET).doesNotContain(product2.getTitle());
    }

    @Test
    public void canRequestWithFilterMultipleResultFromSellerID() {
        product2.setSellerId(VALID_SELLER_ID_1);
        var responseGET = getProductResponse("sellerId", VALID_SELLER_ID_1).readEntity(String.class);

        assertThat(responseGET).contains(product1.getTitle());
        assertThat(responseGET).contains(product2.getTitle());
    }

    @Test
    public void canRequestWithFilterSingleResultFromCategory() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var responseGET = getProductResponse("categories", "sports").readEntity(String.class);

        assertThat(responseGET).contains(product2.getTitle());
        assertThat(responseGET).doesNotContain(product1.getTitle());
    }

    @Test
    public void canRequestWithFilterMultipleResultFromCategories() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var responseGET = getProductResponse("categories", "others").readEntity(String.class);

        assertThat(responseGET).contains(product1.getTitle());
        assertThat(responseGET).contains(product2.getTitle());
    }

    @Test
    public void canRequestWithMultipleFilter() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var response = target(ProductController.PRODUCTS_PATH).queryParam("title", "DT0").queryParam("minPrice", "1").request(MediaType.APPLICATION_JSON_TYPE).get();
        var responseGET = response.readEntity(String.class);

        assertThat(responseGET).contains(product1.getTitle());
        assertThat(responseGET).contains(product2.getTitle());
    }

    @Test
    public void shouldReturn400OnInvalidParameter() {
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var response = target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_SELLER_ID_1).post(Entity.entity(product1, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("minPrice", "patate");

        assertThat(responseGET.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    private Response getProductResponse(String typeOf, Object queryParam) {
        return target(ProductController.PRODUCTS_PATH).queryParam(typeOf, queryParam).request(MediaType.APPLICATION_JSON_TYPE).get();
    }
}