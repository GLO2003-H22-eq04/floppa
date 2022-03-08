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
import ulaval.glo2003.api.seller.SellerController;
import ulaval.glo2003.api.seller.dto.SellerDTO;
import ulaval.glo2003.api.seller.dto.SellerInfoResponseDTO;
import ulaval.glo2003.domain.seller.Seller;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.time.LocalDate;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SellerControllerIntegrationTests extends JerseyTest {

    private final static String VALID_ID = "0";
    private final static String INVALID_ID = "42";

    @Mock
    private SellerRepository sellerListRepositoryMock;

    private SellerDTO aSellerDTO1;
    private SellerDTO aSellerDTO2;

    private Seller seller;

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

        seller = new Seller(aSellerDTO1);

        when(sellerListRepositoryMock.findById(Integer.parseInt(VALID_ID))).thenReturn(Optional.of(seller));
    }

    @Override
    protected Application configure() {
        var resourceConfig = Main.getRessourceConfig();
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(sellerListRepositoryMock).to(SellerRepository.class).ranked(2);
            }
        });
        return resourceConfig;
    }

    @Test
    public void canReceiveUrlOfCreatedSeller() {
        var response = postCreatingSellerResponse(aSellerDTO1);

        var locationHeader = (String) response.getHeaders().getFirst("Location");

        assertThat(locationHeader.contains(VALID_ID)).isTrue();
        assertThat(IntegrationUtils.isUrl(locationHeader)).isTrue();
    }

    @Test
    public void canCreateMultipleSellers() {
        var response1 = postCreatingSellerResponse(aSellerDTO1);
        var response2 = postCreatingSellerResponse(aSellerDTO2);

        var locationHeader1 = (String) response1.getHeaders().getFirst("Location");
        var locationHeader2 = (String) response2.getHeaders().getFirst("Location");

        assertThat(IntegrationUtils.isUrl(locationHeader1)).isTrue();
        assertThat(IntegrationUtils.isUrl(locationHeader2)).isTrue();
    }

    @Test
    public void canReceiveRequestGetSellerNormal() {
        var response = getSellerResponse(Integer.parseInt(VALID_ID));
        var status = response.getStatus();
        var entity = response.readEntity(SellerInfoResponseDTO.class);

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(entity.name).isEqualTo(seller.getName());
        assertThat(entity.bio).isEqualTo(seller.getBio());
        assertThat(entity.createdAt).isEqualTo(seller.getCreatedAt());
    }

    @Test
    public void canRejectRequestGetSellerInvalidId() {
        var response = getSellerResponse(Integer.parseInt(INVALID_ID));
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    private Response postCreatingSellerResponse(SellerDTO sellerDTO) {
        return target(SellerController.SELLERS_PATH).request().post(Entity.entity(sellerDTO, MediaType.APPLICATION_JSON_TYPE));
    }

    private Response getSellerResponse(int sellerId) {
        return target(SellerController.SELLERS_PATH + '/' + sellerId).request().get();
    }
}