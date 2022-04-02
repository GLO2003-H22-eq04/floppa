package ulaval.glo2003;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import ulaval.glo2003.api.health.HealthController;
import ulaval.glo2003.api.product.ProductAssembler;
import ulaval.glo2003.api.product.ProductController;
import ulaval.glo2003.api.seller.SellerController;
import ulaval.glo2003.api.validation.handler.ConstraintViolationExceptionHandler;
import ulaval.glo2003.api.validation.handler.ParamExceptionHandler;
import ulaval.glo2003.api.validation.handler.ProcessingExceptionHandler;
import ulaval.glo2003.api.validation.handler.ValidationExceptionHandler;
import ulaval.glo2003.domain.product.ProductFactory;
import ulaval.glo2003.domain.product.repository.ProductListRepository;
import ulaval.glo2003.domain.product.repository.ProductRepository;
import ulaval.glo2003.domain.seller.repository.SellerListRepository;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.io.IOException;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws IOException {

        final String port = System.getenv("PORT");
        final String baseUri = "http://0.0.0.0:" + port;

        URI uri = URI.create(baseUri);

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, getRessourceConfig());
        server.start();
    }

    public static ResourceConfig getRessourceConfig() {
        return new ResourceConfig()
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(new SellerListRepository()).to(SellerRepository.class);
                        bind(new ProductListRepository()).to(ProductRepository.class);
                        bind(new ProductFactory()).to(ProductFactory.class);
                        bind(new ProductAssembler()).to(ProductAssembler.class);
                    }
                })
                .register(SellerController.class)
                .register(HealthController.class)
                .register(ProductController.class)
                .register(ConstraintViolationExceptionHandler.class)
                .register(ValidationExceptionHandler.class)
                .register(ProcessingExceptionHandler.class)
                .register(ParamExceptionHandler.class);
    }
}
