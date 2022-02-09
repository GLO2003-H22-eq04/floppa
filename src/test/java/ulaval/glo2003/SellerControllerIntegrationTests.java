package ulaval.glo2003;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.regex.Pattern;


public class SellerControllerIntegrationTests extends JerseyTest {

    private SellerDTO sellerDTO;

    @Before
    public void before() {
        sellerDTO = new SellerDTO();
        sellerDTO.name = "testnom";
        sellerDTO.bio = "testbio";
        sellerDTO.birthDate = LocalDate.now().minusYears(20);
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
        var response = target("/sellers").request().post(Entity.entity(sellerDTO, MediaType.APPLICATION_JSON_TYPE));
        var locationHeader = (String) response.getHeaders().getFirst("Location");
        Assert.assertTrue(isUrl(locationHeader));
        Assert.assertEquals(locationHeader.lastIndexOf('0'), locationHeader.length() - 1);
    }

}