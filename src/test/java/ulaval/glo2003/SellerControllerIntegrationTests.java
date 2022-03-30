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
import ulaval.glo2003.applicatif.seller.SellerDto;
import ulaval.glo2003.applicatif.seller.SellerInfoResponseDto;
import ulaval.glo2003.domain.seller.Seller;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SellerControllerIntegrationTests extends JerseyTest {

    private final static UUID VALID_ID = UUID.fromString("34272b55-6b32-477c-8ab8-1f4d09a66949");
    private final static UUID INVALID_ID = UUID.fromString("5a409ca2-7ec8-4d89-a224-0f6894056a42");

    @Mock
    private SellerRepository sellerListRepositoryMock;

    private SellerDto aSellerDTO1;
    private SellerDto aSellerDTO2;

    private Seller seller;

    @Before
    public void before() {
        aSellerDTO1 = new SellerDto();
        aSellerDTO1.name = "Testnom";
        aSellerDTO1.bio = "wow";
        aSellerDTO1.birthDate = LocalDate.now().minusYears(20);

        aSellerDTO2 = new SellerDto();
        aSellerDTO2.name = "Coolnom";
        aSellerDTO2.bio = "amazing";
        aSellerDTO2.birthDate = LocalDate.now().minusYears(30);

        seller = new Seller(aSellerDTO1);

        when(sellerListRepositoryMock.findById(VALID_ID)).thenReturn(Optional.of(seller));
        when(sellerListRepositoryMock.add(any())).thenReturn(VALID_ID);
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

        assertThat(locationHeader.contains(VALID_ID.toString())).isTrue();
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
        var response = getSellerResponse(VALID_ID);
        var status = response.getStatus();
        var entity = response.readEntity(SellerInfoResponseDto.class);

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(entity.name).isEqualTo(seller.getName());
        assertThat(entity.bio).isEqualTo(seller.getBio());
        assertThat(entity.createdAt).isEqualTo(seller.getCreatedAt());
    }

    @Test
    public void canRejectRequestGetSellerInvalidId() {
        var response = getSellerResponse(INVALID_ID);
        var status = response.getStatus();

        assertThat(status).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    private Response postCreatingSellerResponse(SellerDto sellerDTO) {
        return target(SellerController.SELLERS_PATH).request().post(Entity.entity(sellerDTO, MediaType.APPLICATION_JSON_TYPE));
    }

    private Response getSellerResponse(UUID sellerId) {
        return target(SellerController.SELLERS_PATH + '/' + sellerId).request().get();
    }
}