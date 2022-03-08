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

import java.time.LocalDate;

import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.*;

public class UseCasesAcceptationTests extends JerseyTest {

    private SellerDTO sellerDTO;
    private ProductDTO productDTO;
    private Jsonb serializer;

    @Override
    protected Application configure() {
        return Main.getRessourceConfig();
    }

    @Before
    public void before() {
        baseURI = getBaseUri().toString();

        serializer = JsonbBuilder.create();

        sellerDTO = new SellerDTO();
        sellerDTO.name = "Bob";
        sellerDTO.birthDate = LocalDate.of(2000, 10, 10);
        sellerDTO.bio = "Bob eSt cool";

        productDTO = new ProductDTO();
        productDTO.title = "ProDuit";
        productDTO.description = "Cool produit";
        productDTO.suggestedPrice = 10;
    }

    @Test
    public void canReturn201OnCreatedSeller() {
        var request = given().contentType(ContentType.JSON).body(serializer.toJson(sellerDTO));

        var response = request.post(SellerController.SELLERS_PATH).then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.CREATED.getStatusCode());
        assertThat(IntegrationUtils.isUrl(response.header("Location"))).isTrue();
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
        var request = given()
                .contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, "0"));
        given().contentType(ContentType.JSON).body(serializer.toJson(sellerDTO)).post(SellerController.SELLERS_PATH);

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

        given().contentType(ContentType.JSON).body(serializer.toJson(sellerDTO)).post(SellerController.SELLERS_PATH);
        given().contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, "0"))
                .post(ProductController.PRODUCTS_PATH);

        var response = get(SellerController.SELLERS_PATH + "/" + "0").then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.body().jsonPath().getString("products")).isNotEmpty();
    }

    @Test
    public void canReturn200OnRequestedProduct() {
        given().contentType(ContentType.JSON)
                .body(serializer.toJson(sellerDTO))
                .post(SellerController.SELLERS_PATH);

        given().contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, "0"))
                .post(ProductController.PRODUCTS_PATH);

        var response = get(ProductController.PRODUCTS_PATH + "/" + "0").then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void canReturn404OnInexistingRequestedProduct() {
        var response = get(ProductController.PRODUCTS_PATH + "/" + "0").then().extract();

        assertThat(response.statusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void canReturn200WithInclusiveFilteredProduct(){
        given().contentType(ContentType.JSON)
                .body(serializer.toJson(sellerDTO))
                .post(SellerController.SELLERS_PATH);

        given().contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, "0"))
                .post(ProductController.PRODUCTS_PATH);

        var response = given().header(new Header(ProductController.SELLER_ID_HEADER, "0")).get(ProductController.PRODUCTS_PATH+"?title=OdU&minPrice=10&maxPrice=10");

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.jsonPath().getString("products")).isNotEmpty();
    }

    @Test
    public void canReturn200WithoutExclusiveFilteredProduct(){
        given().contentType(ContentType.JSON)
                .body(serializer.toJson(sellerDTO))
                .post(SellerController.SELLERS_PATH);

        given().contentType(ContentType.JSON)
                .body(serializer.toJson(productDTO))
                .header(new Header(ProductController.SELLER_ID_HEADER, "0"))
                .post(ProductController.PRODUCTS_PATH);

        var response = given().header(new Header(ProductController.SELLER_ID_HEADER, "0")).get(ProductController.PRODUCTS_PATH+"?minPrice=11");

        assertThat(response.statusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.jsonPath().getString("products")).isEqualTo("[]");
    }
}
