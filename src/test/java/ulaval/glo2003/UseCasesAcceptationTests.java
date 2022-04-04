package ulaval.glo2003;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.api.product.ProductController;
import ulaval.glo2003.api.seller.SellerController;
import ulaval.glo2003.applicatif.product.ProductDto;
import ulaval.glo2003.applicatif.product.ProductInfoResponseDto;
import ulaval.glo2003.applicatif.seller.SellerDto;

import java.time.LocalDate;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.*;

public class UseCasesAcceptationTests extends JerseyTest {

    private final static UUID NON_USED_ID = UUID.fromString("84850a41-7948-41d6-b9fd-c193dd6e4171");
    private SellerDto sellerDTO;
    private ProductDto productDTO;
    private Jsonb serializer;
    private SellerCreationResponse sellerCreationResponse;
    private io.restassured.response.Response productCreationResponse;

    @Override
    protected Application configure() {
        return Main.getRessourceConfig(Main.loadConfig());
    }

    @Before
    public void before() {
        baseURI = getBaseUri().toString();

        serializer = JsonbBuilder.create();

        sellerDTO = new SellerDto();
        sellerDTO.name = "Bob";
        sellerDTO.birthDate = LocalDate.of(2000, 10, 10);
        sellerDTO.bio = "Bob eSt cool";

        productDTO = new ProductDto();
        productDTO.title = "ProDuit";
        productDTO.description = "Cool produit";
        productDTO.suggestedPrice = 10;
    }

    @Test
    public void canReturn201OnCreatedSeller() {
        var response = createSeller();

        assertThat(response.statusCode).isEqualTo(Response.Status.CREATED.getStatusCode());
        assertThat(IntegrationUtils.isUrl(response.location)).isTrue();
    }

    @Test
    public void shouldReturn400OnInvalidCreatedSeller() {
        sellerDTO.name = "";
        var request = given()
                .contentType(ContentType.JSON)
                .body(serializer.toJson(sellerDTO));

        var response = request.post(SellerController.SELLERS_PATH).then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(response.body().jsonPath().getString("code")).isEqualTo("INVALID_PARAMETER");
    }

    @Test
    public void canReturn201OnCreatedProduct() {
        var sellerCreationResponse = createSeller();

        var request = given()
                .contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, sellerCreationResponse.id.toString()));

        var response = request.post(ProductController.PRODUCTS_PATH).then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.CREATED.getStatusCode());
    }

    @Test
    public void shouldReturn400OnInvalidProductId() {
        var request = given()
                .contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, "patate"));

        var response = request.post(ProductController.PRODUCTS_PATH).then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(response.body().jsonPath().getString("code")).isEqualTo("INVALID_PARAMETER");
    }

    @Test
    public void canReturn200AndRequestedSellerWithProduct() {
        var seller = createSeller();

        given().contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, seller.id.toString()))
                .post(ProductController.PRODUCTS_PATH);

        var response = get(SellerController.SELLERS_PATH + "/" + seller.id).then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.body().jsonPath().getString("products")).isNotEmpty();
    }

    @Test
    public void canReturn200OnRequestedProduct() {
        var sellerCreationResponse = createSeller();

        var productCreationResponse = given().contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, sellerCreationResponse.id.toString()))
                .post(ProductController.PRODUCTS_PATH);

        String location = productCreationResponse.header("Location");
        var productId = location.substring(location.lastIndexOf('/') + 1);

        var response = get(ProductController.PRODUCTS_PATH + "/" + productId).then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void canReturn404OnInexistingRequestedProduct() {
        var response = get(ProductController.PRODUCTS_PATH + "/" + NON_USED_ID).then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void canReturn200WithInclusiveFilteredProduct() {
        given().contentType(ContentType.JSON)
                .body(serializer.toJson(sellerDTO))
                .post(SellerController.SELLERS_PATH);

        given().contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, "0"))
                .post(ProductController.PRODUCTS_PATH);

        var response = given().header(new Header(ProductController.SELLER_ID_HEADER, "0")).get(ProductController.PRODUCTS_PATH + "?title=OdU&minPrice=10&maxPrice=10");

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.jsonPath().getString("products")).isNotEmpty();
    }

    @Test
    public void canReturn200WithoutExclusiveFilteredProduct() {
        given().contentType(ContentType.JSON)
                .body(serializer.toJson(sellerDTO))
                .post(SellerController.SELLERS_PATH);

        given().contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, "0"))
                .post(ProductController.PRODUCTS_PATH);

        var response = given().header(new Header(ProductController.SELLER_ID_HEADER, "0")).get(ProductController.PRODUCTS_PATH + "?minPrice=11");

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.jsonPath().getString("products")).isEqualTo("[]");
    }

    @Test
    public void canReturn200WithMinMaxMeanNullOnProductWithNoOffers() {
        createSellerWithProduct();

        var response = given().get(productCreationResponse.header("Location"));
        var body = response.body().asString();
        var responseDto = serializer.fromJson(body, ProductInfoResponseDto.class);

        assertThat(responseDto.offers.max).isNull();
        assertThat(responseDto.offers.min).isNull();
        assertThat(responseDto.offers.mean).isNull();
    }

    private void createSellerWithProduct() {
        var sellerCreationResponse = given().contentType(ContentType.JSON)
                .body(serializer.toJson(sellerDTO))
                .post(SellerController.SELLERS_PATH);
        this.sellerCreationResponse = new SellerCreationResponse(sellerCreationResponse);
        productCreationResponse = given().contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, getIdFromUrl(sellerCreationResponse.header("Location"))))
                .post(ProductController.PRODUCTS_PATH);
    }

    private String getIdFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }

    private SellerCreationResponse createSeller() {
        var sellerCreationResponse = given().contentType(ContentType.JSON).body(serializer.toJson(sellerDTO)).post(SellerController.SELLERS_PATH);
        var location = sellerCreationResponse.header("Location");
        var sellerId = UUID.fromString(location.substring(location.lastIndexOf('/') + 1));
        return new SellerCreationResponse(location, sellerId, sellerCreationResponse.statusCode());
    }
}

class SellerCreationResponse {
    public String location;
    public UUID id;
    public int statusCode;

    public SellerCreationResponse(io.restassured.response.Response response) {
        this.location = response.header("Location");
        this.id = getIdFromUrl(location);
        this.statusCode = response.statusCode();
    }

    private UUID getIdFromUrl(String url) {
        var id = url.substring(url.lastIndexOf('/') + 1);
        return UUID.fromString(id);
    }

}

class ProductCreationResponse {
    public String location;
    public UUID id;
    public int statusCode;

    public ProductCreationResponse(io.restassured.response.Response response) {
        this.location = location;
        this.id = id;
        this.statusCode = statusCode;
    }