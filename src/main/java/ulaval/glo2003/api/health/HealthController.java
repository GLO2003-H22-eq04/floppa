package ulaval.glo2003.api.health;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("health")
public class HealthController {

    @GET
    public Response healthRequest() {
        return Response
                .status(Response.Status.OK)
                .entity("OK")
                .build();
    }
}