package ulaval.glo2003.api.product;

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
import ulaval.glo2003.applicatif.dto.product.ProductDto;
import ulaval.glo2003.applicatif.dto.product.ProductFilterResponsesCollectionDto;
import ulaval.glo2003.applicatif.dto.product.ProductInfoResponseDto;
import ulaval.glo2003.applicatif.dto.seller.SellerDto;
import ulaval.glo2003.domain.product.Amount;
import ulaval.glo2003.domain.product.Product;
import ulaval.glo2003.domain.product.ProductCategory;
import ulaval.glo2003.domain.product.ProductFactory;
import ulaval.glo2003.domain.product.repository.ProductRepository;
import ulaval.glo2003.domain.seller.Seller;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerIntegrationTests extends JerseyTest {


    private final static UUID VALID_PRODUCT_ID = UUID.fromString("e372e035-1072-485a-9fbc-473fd9017658");

    private final static UUID VALID_SELLER_ID = UUID.fromString("2f3ca1d6-1625-4de4-b08c-5f3804b254c9");
    private final static UUID INVALID_SELLER_ID = UUID.fromString("8461f7d8-4cbb-4b35-806e-9073cd98afc2");
    private final static UUID MISSING_ID = UUID.fromString("93dad72a-838e-4623-8afa-47a089cac44c");

    @Mock
    private SellerRepository sellerListRepositoryMock;

    @Mock
    private ProductRepository productListRepositoryMock;

    @Mock
    private ProductFactory productFactoryMock;

    private ProductDto productDTO1;
    private ProductDto productDTO2;
    private Product product;
    private Product product2;

    @Before
    public void before() {
        productDTO1 = new ProductDto();
        productDTO1.title = "titleDT01";
        productDTO1.description = "descriptionDT01";
        productDTO1.suggestedPrice = 4.29;
        productDTO1.categories = List.of("beauty");

        productDTO2 = new ProductDto();
        productDTO2.title = "titleDT02";
        productDTO2.description = "descriptionDT02";
        productDTO2.suggestedPrice = 6.49;
        productDTO2.categories = List.of("sports");

        product = new Product(Instant.now().atOffset(ZoneOffset.UTC));
        product.setProductId(VALID_PRODUCT_ID);
        product.setDescription("Un produit");
        product.setTitle("Produit");
        product.setSellerId(VALID_SELLER_ID);
        product.setSuggestedPrice(new Amount(5.01));

        product2 = new Product(OffsetDateTime.parse("2022-04-06T18:20:43.149Z"));
        product2.setProductId(VALID_PRODUCT_ID);
        product2.setDescription("Old and dirty");
        product2.setTitle("Socks");
        product2.setSellerId(VALID_SELLER_ID);
        product2.setSuggestedPrice(new Amount(2.99));
        product2.addCategory(ProductCategory.APPAREL);

        var sellerDTO = new SellerDto();
        sellerDTO.name = "John Doe";
        sellerDTO.bio = "Un seller";
        sellerDTO.birthDate = LocalDate.now().minusYears(20);

        var seller = new Seller(sellerDTO);

        when(sellerListRepositoryMock.existById(VALID_SELLER_ID)).thenReturn(true);
        when(sellerListRepositoryMock.existById(INVALID_SELLER_ID)).thenReturn(false);
        when(sellerListRepositoryMock.findById(VALID_SELLER_ID)).thenReturn(Optional.of(seller));

        when(productListRepositoryMock.findAll()).thenReturn(List.of(product, product2));

        when(productListRepositoryMock.add(any())).thenReturn(VALID_SELLER_ID);
        when(productListRepositoryMock.findById(VALID_SELLER_ID)).thenReturn(Optional.of(product));
        when(productListRepositoryMock.findById(MISSING_ID)).thenReturn(Optional.empty());

        when(productFactoryMock.createProduct(any(ProductDto.class), eq(VALID_SELLER_ID))).thenReturn(product);
    }

    @Override
    protected Application configure() {
        var resourceConfig = Main.getRessourceConfig(Main.loadConfig());
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
        var response = postCreatingProductResponse(productDTO1, VALID_SELLER_ID);

        var locationHeader = (String) response.getHeaders().getFirst("Location");

        assertThat(locationHeader.contains(VALID_SELLER_ID.toString())).isTrue();
        assertThat(IntegrationUtils.isUrl(locationHeader)).isTrue();
    }

    @Test
    public void canRejectRequestOnNullProduct() {
        var response = postCreatingProductResponse(null, VALID_SELLER_ID);

        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnUnknownSellerId() {
        var response = postCreatingProductResponse(productDTO1, INVALID_SELLER_ID);

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

        var response = postCreatingProductResponse(productDTO2, VALID_SELLER_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidTitle() {
        productDTO2.title = "";

        var response = postCreatingProductResponse(productDTO2, VALID_SELLER_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnNullTitle() {
        productDTO2.title = null;

        var response = postCreatingProductResponse(productDTO2, VALID_SELLER_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidDescription() {
        productDTO2.description = "";

        var response = postCreatingProductResponse(productDTO2, VALID_SELLER_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canRejectRequestOnNullDescription() {
        productDTO2.description = null;

        var response = postCreatingProductResponse(productDTO2, VALID_SELLER_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canGetExistingProduct() {
        var response = getSellerResponse(VALID_SELLER_ID);

        var entity = response.readEntity(ProductInfoResponseDto.class);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(entity.id).isEqualTo(product.getProductId());
        assertThat(entity.title).isEqualTo(product.getTitle());
        assertThat(entity.categories).isEqualTo(product.getCategories());
        assertThat(entity.createdAt).isEqualTo(product.getCreatedAt());
        assertThat(entity.suggestedPrice).isEqualTo(product.getSuggestedPrice().getValue());
        assertThat(entity.description).isEqualTo(product.getDescription());
        assertThat(entity.seller.id).isEqualTo(product.getSellerId());
    }

    @Test
    public void canGetExistingProductNoFilter() {
        var response = getProductResponse("", "");

        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void canGetFilteredProductFromMinPrice() {
        var response = getProductResponse("minPrice", "5");

        var entity = response.readEntity(ProductFilterResponsesCollectionDto.class);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());

        assertThat(entity.products.size()).isEqualTo(1);
        var result = entity.products.get(0);
        assertThat(result.id).isEqualTo(product.getProductId());
        assertThat(result.title).isEqualTo(product.getTitle());
        assertThat(result.categories).isEqualTo(product.getCategories());
        assertThat(result.createdAt).isEqualTo(product.getCreatedAt());
        assertThat(result.suggestedPrice).isEqualTo(product.getSuggestedPrice().getValue());
        assertThat(result.description).isEqualTo(product.getDescription());
        assertThat(result.seller.id).isEqualTo(product.getSellerId());
        assertThat(result.offers.count).isEqualTo(product.getOffers().getCount());
    }

    @Test
    public void canGetFilteredProductFromMaxPrice() {
        var response = getProductResponse("maxPrice", "3");

        var entity = response.readEntity(ProductFilterResponsesCollectionDto.class);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());

        assertThat(entity.products.size()).isEqualTo(1);
        var result = entity.products.get(0);
        assertThat(result.id).isEqualTo(product2.getProductId());
        assertThat(result.title).isEqualTo(product2.getTitle());
        assertThat(result.categories).isEqualTo(product2.getCategories());
        assertThat(result.createdAt).isEqualTo(product2.getCreatedAt());
        assertThat(result.suggestedPrice).isEqualTo(product2.getSuggestedPrice().getValue());
        assertThat(result.description).isEqualTo(product2.getDescription());
        assertThat(result.seller.id).isEqualTo(product2.getSellerId());
        assertThat(result.offers.count).isEqualTo(product2.getOffers().getCount());
    }

    @Test
    public void canGetFilteredProductFromTitle() {
        var response = getProductResponse("title", "Socks");

        var entity = response.readEntity(ProductFilterResponsesCollectionDto.class);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());

        assertThat(entity.products.size()).isEqualTo(1);
        var result = entity.products.get(0);
        assertThat(result.id).isEqualTo(product2.getProductId());
        assertThat(result.title).isEqualTo(product2.getTitle());
        assertThat(result.categories).isEqualTo(product2.getCategories());
        assertThat(result.createdAt).isEqualTo(product2.getCreatedAt());
        assertThat(result.suggestedPrice).isEqualTo(product2.getSuggestedPrice().getValue());
        assertThat(result.description).isEqualTo(product2.getDescription());
        assertThat(result.seller.id).isEqualTo(product2.getSellerId());
        assertThat(result.offers.count).isEqualTo(product2.getOffers().getCount());
    }

    @Test
    public void canGetFilteredProductFromCategories() {
        var response = getProductResponse("categories", ProductCategory.APPAREL.getName());

        var entity = response.readEntity(ProductFilterResponsesCollectionDto.class);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());

        assertThat(entity.products.size()).isEqualTo(1);
        var result = entity.products.get(0);
        assertThat(result.id).isEqualTo(product2.getProductId());
        assertThat(result.title).isEqualTo(product2.getTitle());
        assertThat(result.categories).isEqualTo(product2.getCategories());
        assertThat(result.createdAt).isEqualTo(product2.getCreatedAt());
        assertThat(result.suggestedPrice).isEqualTo(product2.getSuggestedPrice().getValue());
        assertThat(result.description).isEqualTo(product2.getDescription());
        assertThat(result.seller.id).isEqualTo(product2.getSellerId());
        assertThat(result.offers.count).isEqualTo(product2.getOffers().getCount());
    }

    @Test
    public void canGetFilteredProductFromSellerID() {
        var response = getProductResponse("sellerId", VALID_SELLER_ID.toString());

        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void shouldRejectNonExistingProduct() {
        var response = getSellerResponse(MISSING_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    private Response postCreatingProductResponse(ProductDto productDTO, UUID sellerId) {
        return postCreatingProductResponse(productDTO, sellerId.toString());
    }

    private Response postCreatingProductResponse(ProductDto productDTO, String sellerId) {
        return target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, sellerId).post(Entity.entity(productDTO, MediaType.APPLICATION_JSON_TYPE));
    }

    private Response getSellerResponse(UUID productId) {
        return target(ProductController.PRODUCTS_PATH + '/' + productId).request().get();
    }

    private Response getProductResponse(String paramName, String paramValue) {
        return target(ProductController.PRODUCTS_PATH).queryParam(paramName, paramValue).request().get();
    }
}