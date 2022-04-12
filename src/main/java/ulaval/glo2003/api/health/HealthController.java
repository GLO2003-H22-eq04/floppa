package ulaval.glo2003.api.health;

import com.mongodb.MongoException;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import ulaval.glo2003.applicatif.health.HealthDto;
import ulaval.glo2003.domain.config.DatastoreFactory;

@Path(HealthController.HEALTH_PATH)
public class HealthController {

    public static final String HEALTH_PATH = "/health";

    @Inject
    private DatastoreFactory datastoreFactory;

    @GET
    public Response healthRequest() {
        var healthDto = new HealthDto(true, getDatabaseStatus());

        return Response.status(healthDto.db ? Response.Status.OK : Response.Status.INTERNAL_SERVER_ERROR)
                .entity(healthDto)
                .build();
    }

    private boolean getDatabaseStatus() {
        try {
            var database = datastoreFactory.getDatastore().getDatabase();
            var command = new BsonDocument("ping", new BsonInt64(1));
            database.runCommand(command);
            return true;
        } catch (MongoException me) {
            return false;
        }
    }
}