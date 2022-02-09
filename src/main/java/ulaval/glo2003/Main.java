package ulaval.glo2003;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import ulaval.glo2003.Validation.Handler.ConstraintViolationExceptionHandler;
import ulaval.glo2003.Validation.Handler.ProcessingExceptionHandler;
import ulaval.glo2003.Validation.Handler.ValidationExceptionHandler;

import java.io.IOException;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws IOException {
        URI uri = URI.create("http://localhost:8080/");

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, getRessourceConfig());
        server.start();
    }

    public static ResourceConfig getRessourceConfig(){
        return new ResourceConfig()
                .register(SellerController.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(new SellerListRepository()).to(SellerRepository.class);
                    }
                })
                .register(HealthController.class)
                .register(ConstraintViolationExceptionHandler.class)
                .register(ValidationExceptionHandler.class)
                .register(ProcessingExceptionHandler.class);
    }
}
