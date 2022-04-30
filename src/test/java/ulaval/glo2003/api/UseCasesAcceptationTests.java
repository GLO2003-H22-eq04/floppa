package ulaval.glo2003.api;

import com.google.common.truth.Truth;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import ulaval.glo2003.IntegrationUtils;
import ulaval.glo2003.Main;
import ulaval.glo2003.api.product.ProductController;
import ulaval.glo2003.api.seller.SellerController;
import ulaval.glo2003.applicatif.dto.product.ProductDto;
import ulaval.glo2003.applicatif.dto.product.ProductInfoResponseDto;
import ulaval.glo2003.applicatif.dto.seller.SellerDto;
import ulaval.glo2003.applicatif.validation.ErrorDto;

import java.time.LocalDate;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.*;

public class UseCasesAcceptationTests extends JerseyTest {

    private final static UUID NON_USED_ID = UUID.fromString("84850a41-7948-41d6-b9fd-c193dd6e4171");
    private static final String MISSING_PARAMETER_CODE = "MISSING_PARAMETER";
    private static final String INVALID_PARAMETER_CODE = "INVALID_PARAMETER";
    private SellerDto sellerDTO;
    private ProductDto productDTO;
    private Jsonb serializer;
    private CreationResponse createdSeller;
    private CreationResponse createdProduct;

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
        createSeller();

        assertThat(createdSeller.statusCode).isEqualTo(Response.Status.CREATED.getStatusCode());
        Truth.assertThat(IntegrationUtils.isUrl(createdSeller.location)).isTrue();
    }

    @Test
    public void shouldReturn400OnInvalidCreatedSeller() {
        sellerDTO.name = "";
        createSeller();

        assertThat(createdSeller.statusCode).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(createdSeller.response.body().jsonPath().getString("code")).isEqualTo("INVALID_PARAMETER");
    }

    @Test
    public void canReturn201OnCreatedProduct() {
        createSellerWithProduct();

        assertThat(createdProduct.statusCode).isEqualTo(Response.Status.CREATED.getStatusCode());
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
        createSellerWithProduct();

        var response = get(SellerController.SELLERS_PATH + "/" + createdSeller.id.toString()).then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.body().jsonPath().getString("products")).isNotEmpty();
    }

    @Test
    public void canReturn200OnRequestedProduct() {
        createSellerWithProduct();

        var response = get(ProductController.PRODUCTS_PATH + "/" + createdProduct.id.toString()).then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void canReturn404OnInexistingRequestedProduct() {
        var response = get(ProductController.PRODUCTS_PATH + "/" + NON_USED_ID).then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void canReturn200WithInclusiveFilteredProduct() {
        createSellerWithProduct();

        var response = given().header(new Header(ProductController.SELLER_ID_HEADER, createdSeller.id.toString()))
                .get(ProductController.PRODUCTS_PATH + "?title=OdU&minPrice=10&maxPrice=10");

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.jsonPath().getString("products")).isNotEmpty();
    }

    @Test
    public void canReturn200WithoutExclusiveFilteredProduct() {
        createSellerWithProduct();

        var response = given().header(new Header(ProductController.SELLER_ID_HEADER, createdSeller.id.toString()))
                .get(ProductController.PRODUCTS_PATH + "?minPrice=11");

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.jsonPath().getString("products")).isEqualTo("[]");
    }

    @Test
    public void canReturn200WithMinMaxMeanNullOnProductWithNoOffers() {
        createSellerWithProduct();

        var response = given().get(createdProduct.location);
        var body = response.body().asString();
        var responseDto = serializer.fromJson(body, ProductInfoResponseDto.class);

        assertThat(responseDto.offers.max).isNull();
        assertThat(responseDto.offers.min).isNull();
        assertThat(responseDto.offers.mean).isNull();
    }

    @Test
    public void canReturn400WithMissingErrorCodeOnMissingNameOfPostSeller() {
        sellerDTO.name = null;
        createSeller();
        assertThat(createdSeller.statusCode).isEqualTo(400);
        assertThat(createdSeller.error.code).isEqualTo(MISSING_PARAMETER_CODE);
    }

    @Test
    public void canReturn400WithInvalidErrorCodeOnInvalidNameOfPostSeller() {
        sellerDTO.name = " ";
        createSeller();
        assertThat(createdSeller.statusCode).isEqualTo(400);
        assertThat(createdSeller.error.code).isEqualTo(INVALID_PARAMETER_CODE);
    }

    private void createSellerWithProduct() {
        createSeller();
        createProduct(createdSeller);
    }

    private void createProduct(CreationResponse seller) {
        var response = given().contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, getIdFromUrl(seller.id.toString())))
                .post(ProductController.PRODUCTS_PATH);

        createdProduct = new CreationResponse(response);
    }

    private String getIdFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }

    private void createSeller() {
        var sellerCreationResponse = given().contentType(ContentType.JSON)
                .body(serializer.toJson(sellerDTO))
                .post(SellerController.SELLERS_PATH);
        this.createdSeller = new CreationResponse(sellerCreationResponse);
    }
}

class CreationResponse {
    public final boolean success;
    public final ErrorDto error;
    public final String location;
    public final UUID id;
    public final int statusCode;
    public final io.restassured.response.Response response;

    public CreationResponse(io.restassured.response.Response response) {
        this.location = response.header("Location");
        if (location != null)
            this.id = getIdFromUrl(location);
        else
            this.id = null;

        this.statusCode = response.statusCode();
        this.response = response;
        success = statusCode == 201;

        if (!success) {
            error = response.body().as(ErrorDto.class);
        } else
            error = null;
    }

    private UUID getIdFromUrl(String url) {
        var id = url.substring(url.lastIndexOf('/') + 1);
        return UUID.fromString(id);
    }

}