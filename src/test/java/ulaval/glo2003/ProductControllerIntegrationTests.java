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
import java.util.regex.Pattern;

import static com.google.common.truth.Truth.assertThat;


public class ProductControllerIntegrationTests extends JerseyTest {

    private ProductDTO productDTO1;
    private ProductDTO productDTO2;
    private ProductDTO invalidProductDTO;
    private SellerDTO aSellerDTO1;

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

        invalidProductDTO = new ProductDTO();
        invalidProductDTO.title = "";
        invalidProductDTO.description = "";
        invalidProductDTO.suggestedPrice = 1.29;
        invalidProductDTO.categories = List.of("test");

        aSellerDTO1 = new SellerDTO();
        aSellerDTO1.name = "nomDT01";
        aSellerDTO1.bio = "bioDT01";
        aSellerDTO1.birthDate = LocalDate.now().minusYears(20);
    }

    @Override
    protected Application configure() {
        return Main.getRessourceConfig();
    }

    private boolean isUrl(String sequence) {
        try {
            var pattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
            var matcher = pattern.matcher(sequence);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    public void canReceiveUrlOfCreatedProduct() {
        createSeller();
        var response = getResponse(productDTO1, "0");
        var locationHeader = (String) response.getHeaders().getFirst("Location");
        assertThat(isUrl(locationHeader)).isTrue();
    }

    @Test
    public void canRejectRequestOnInvalidHeader() {
        var response = getResponse(productDTO1, "42");
        var status = response.getStatus();
        assertThat(status).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void canRejectRequestOnInvalidBody() {
        var response = getResponse(invalidProductDTO, "13");
        var status = response.getStatus();
        assertThat(status).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void canCreateMultipleProducts() {
        createSeller();
        var response1 = getResponse(productDTO1, "0");
        var response2 = getResponse(productDTO2, "0");

        var locationHeader1 = (String) response1.getHeaders().getFirst("Location");
        var locationHeader2 = (String) response2.getHeaders().getFirst("Location");

        var isUrl1 = isUrl(locationHeader1);
        var isUrl2 = isUrl(locationHeader2);
        assertThat(locationHeader1).isNotEqualTo(locationHeader2);
        assertThat(isUrl1).isTrue();
        assertThat(isUrl2).isTrue();
    }


    private void createSeller(){
        target(SellerController.SELLERS_PATH).request().post(Entity.entity(aSellerDTO1, MediaType.APPLICATION_JSON_TYPE));
    }

    private Response getResponse(ProductDTO productDTO, String sellerId){
        return target(ProductController.PRODUCTS_PATH).request().header(ProductController.SELLER_ID_HEADER, sellerId).post(Entity.entity(productDTO, MediaType.APPLICATION_JSON_TYPE));
    }
}