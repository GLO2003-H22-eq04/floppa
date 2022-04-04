package ulaval.glo2003;

import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ulaval.glo2003.api.health.HealthController;
import ulaval.glo2003.applicatif.health.HealthDto;
import ulaval.glo2003.domain.config.DatastoreFactory;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HealthControllerIntegrationTests extends JerseyTest {

    @Mock
    private DatastoreFactory datastoreFactory;

    @Mock
    private Datastore datastore;

    @Mock
    private MongoDatabase database;

    @Before
    public void before() {
        when(datastoreFactory.getDatastore()).thenReturn(datastore);
        when(datastore.getDatabase()).thenReturn(database);
    }

    @Override
    protected Application configure() {
        return Main.getRessourceConfig(Main.loadConfig()).register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(datastoreFactory).to(DatastoreFactory.class).ranked(2);
            }
        });
    }

    @Test
    public void canReceiveHealthResponseOk() {
        var response = getHealthResponse();

        var status = response.getStatus();
        var entity = response.readEntity(HealthDto.class);

        assertThat(status).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(entity.api).isTrue();
        assertThat(entity.bd).isTrue();
    }

    @Test
    public void canRejectHealthResponseInternalServerError() {
        when(datastore.getDatabase()).thenThrow(MongoException.class);
        var response = getHealthResponse();

        var status = response.getStatus();
        var entity = response.readEntity(HealthDto.class);

        assertThat(status).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        assertThat(entity.api).isTrue();
        assertThat(entity.bd).isFalse();
    }

    private Response getHealthResponse() {
        return target(HealthController.HEALTH_PATH).request().get();
    }
}