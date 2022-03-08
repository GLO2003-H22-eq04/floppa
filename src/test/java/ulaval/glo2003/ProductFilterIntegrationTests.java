package ulaval.glo2003;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ProductFilterIntegrationTests extends JerseyTest {

    private final static String VALID_ID = "0";
    private final static String VALID_ID2 = "1";

    private ProductDTO productDTO1;
    private ProductDTO productDTO2;
    private SellerDTO sellerDTO1;
    private SellerDTO sellerDTO2;

    @Before
    public void before() {
        productDTO1 = new ProductDTO();
        productDTO1.title = "titleDT01";
        productDTO1.description = "descriptionDT01";
        productDTO1.suggestedPrice = 4.29;
        productDTO1.categories = List.of("beauty", "others");

        productDTO2 = new ProductDTO();
        productDTO2.title = "titleDT02";
        productDTO2.description = "descriptionDT02";
        productDTO2.suggestedPrice = 6.49;
        productDTO2.categories = List.of("sports", "others");

        sellerDTO1 = new SellerDTO();
        sellerDTO1.name = "John Doe";
        sellerDTO1.bio = "Un seller";
        sellerDTO1.birthDate = LocalDate.now().minusYears(20);

        sellerDTO2 = new SellerDTO();
        sellerDTO2.name = "John Moe";
        sellerDTO2.bio = "Un seller";
        sellerDTO2.birthDate = LocalDate.now().minusYears(30);

    }

    @Override
    protected Application configure() {
        return Main.getRessourceConfig();
    }


    @Test
    public void canRequestWithoutArgs(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("","");

        assertThat(responseGET.readEntity(String.class)).contains(productDTO1.title);
    }

    @Test
    public void canRequestWithFilterSingleResultFromTitle(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("title","titleDT01").readEntity(String.class);

        assertThat(responseGET).contains(productDTO1.title);
        assertThat(responseGET).doesNotContain(productDTO2.title);
    }

    @Test
    public void canRequestWithFilterManyResultFromTitle(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("title","DT0").readEntity(String.class);

        assertThat(responseGET).contains(productDTO1.title);
        assertThat(responseGET).contains(productDTO2.title);
    }

    @Test
    public void canRequestWithFilterSingleResultFromMaxPrice(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("maxPrice",5).readEntity(String.class);

        assertThat(responseGET).contains(productDTO1.title);
        assertThat(responseGET).doesNotContain(productDTO2.title);
    }

    @Test
    public void canRequestWithFilterMultipleResultFromMaxPrice(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("maxPrice",10).readEntity(String.class);

        assertThat(responseGET).contains(productDTO1.title);
        assertThat(responseGET).contains(productDTO2.title);
    }


    @Test
    public void canRequestWithFilterSingleResultFromMinPrice(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("minPrice",5).readEntity(String.class);

        assertThat(responseGET).doesNotContain(productDTO1.title);
        assertThat(responseGET).contains(productDTO2.title);
    }

    @Test
    public void canRequestWithFilterMultipleResultFromMinPrice(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("minPrice",1).readEntity(String.class);

        assertThat(responseGET).contains(productDTO1.title);
        assertThat(responseGET).contains(productDTO2.title);
    }

    @Test
    public void canRequestWithFilterSingleResultFromSellerID(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));
        target("/sellers").request().post(Entity.entity(sellerDTO2, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID2).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("sellerId",VALID_ID).readEntity(String.class);

        assertThat(responseGET).contains(productDTO1.title);
        assertThat(responseGET).doesNotContain(productDTO2.title);
    }

    @Test
    public void canRequestWithFilterMultipleResultFromSellerID(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));
        target("/sellers").request().post(Entity.entity(sellerDTO2, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("sellerId",VALID_ID).readEntity(String.class);

        assertThat(responseGET).contains(productDTO1.title);
        assertThat(responseGET).contains(productDTO2.title);
    }

    @Test
    public void canRequestWithFilterSingleResultFromCategory(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("categories","sports").readEntity(String.class);

        assertThat(responseGET).contains(productDTO2.title);
        assertThat(responseGET).doesNotContain(productDTO1.title);
    }

    @Test
    public void canRequestWithFilterMultipleResultFromCategories(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("categories","others").readEntity(String.class);

        assertThat(responseGET).contains(productDTO1.title);
        assertThat(responseGET).contains(productDTO2.title);
    }

    @Test
    public void canRequestWithMultipleFilter(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO2, MediaType.APPLICATION_JSON_TYPE));
        var response = target(ProductController.PRODUCTS_PATH).queryParam("title","DT0").queryParam("minPrice","1").request(MediaType.APPLICATION_JSON_TYPE).get();
        var responseGET = response.readEntity(String.class);

        assertThat(responseGET).contains(productDTO1.title);
        assertThat(responseGET).contains(productDTO2.title);
    }

    @Test
    public void badRequest(){
        target("/sellers").request().post(Entity.entity(sellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var response = target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, VALID_ID).post(Entity.entity(productDTO1, MediaType.APPLICATION_JSON_TYPE));
        var responseGET = getProductResponse("minPrice","patate");

        assertThat(responseGET.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    private Response getProductResponse(String typeOf, Object queryParam) {
        return target(ProductController.PRODUCTS_PATH).queryParam(typeOf, queryParam).request(MediaType.APPLICATION_JSON_TYPE).get();
    }
}