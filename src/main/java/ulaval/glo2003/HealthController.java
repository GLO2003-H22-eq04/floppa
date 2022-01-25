package ulaval.glo2003;

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