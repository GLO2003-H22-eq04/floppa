package ulaval.glo2003;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import java.io.IOException;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws IOException {
        ResourceConfig resourceConfig = new ResourceConfig()
                .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
                .register(SellerController.class)
                .register(HealthController.class);

        URI uri = URI.create("http://localhost:8080/");

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig);
        server.start();
    }
}
