package ulaval.glo2003;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static com.google.common.truth.Truth.assertThat;


public class SellerControllerIntegrationTest extends JerseyTest {

    private SellerDTO aSellerDTO1;
    private SellerDTO aSellerDTO2;
    private SellerDTO aSellerDTO3;

    @Before
    public void before() {
        aSellerDTO1 = new SellerDTO();
        aSellerDTO1.name = "Testnom";
        aSellerDTO1.bio = "wow";
        aSellerDTO1.birthDate = LocalDate.now().minusYears(20);

        aSellerDTO2 = new SellerDTO();
        aSellerDTO2.name = "Coolnom";
        aSellerDTO2.bio = "amazing";
        aSellerDTO2.birthDate = LocalDate.now().minusYears(30);

        aSellerDTO3 = new SellerDTO();
        aSellerDTO3.name = "Magnifiquenom";
        aSellerDTO3.bio = "yeehaw";
        aSellerDTO3.birthDate = LocalDate.now().minusYears(40);
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
    public void canReceiveUrlOfCreatedSeller() {
        var response = target("/sellers").request().post(Entity.entity(aSellerDTO1, MediaType.APPLICATION_JSON_TYPE));
        var locationHeader = (String) response.getHeaders().getFirst("Location");
        assertThat(isUrl(locationHeader)).isTrue();
    }

    @Test
    public void canCreateMultipleSellers() {
        var response1 = target("/sellers").request().post(Entity.entity(aSellerDTO1, MediaType.APPLICATION_JSON_TYPE));
        var response2 = target("/sellers").request().post(Entity.entity(aSellerDTO1, MediaType.APPLICATION_JSON_TYPE));
        var response3 = target("/sellers").request().post(Entity.entity(aSellerDTO1, MediaType.APPLICATION_JSON_TYPE));

        var locationHeader1 = (String) response1.getHeaders().getFirst("Location");
        var locationHeader2 = (String) response2.getHeaders().getFirst("Location");
        var locationHeader3 = (String) response3.getHeaders().getFirst("Location");

        assertThat(isUrl(locationHeader1)).isTrue();
        assertThat(isUrl(locationHeader2)).isTrue();
        assertThat(isUrl(locationHeader3)).isTrue();
    }

}