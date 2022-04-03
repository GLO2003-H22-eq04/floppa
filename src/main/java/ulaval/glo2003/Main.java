package ulaval.glo2003;

import jakarta.ws.rs.core.UriBuilder;
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
import ulaval.glo2003.domain.config.DatastoreFactory;
import ulaval.glo2003.domain.config.EnvironmentProperties;
import ulaval.glo2003.domain.config.EnvironmentPropertiesMapper;
import ulaval.glo2003.domain.product.ProductFactory;
import ulaval.glo2003.domain.product.repository.ProductMongodbRepository;
import ulaval.glo2003.domain.product.repository.ProductRepository;
import ulaval.glo2003.domain.seller.repository.SellerMongodbRepository;
import ulaval.glo2003.domain.seller.repository.SellerRepository;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var config = loadConfig();

        var port = System.getenv("PORT");
        if (port == null)
            port = "8080";

        var uri = UriBuilder.fromPath(config.apiBaseUrl).port(Integer.parseInt(port)).build();

        var server = GrizzlyHttpServerFactory.createHttpServer(uri, getRessourceConfig(config));
        server.start();
    }

    public static EnvironmentProperties loadConfig() {
        var envName = System.getenv("env");
        if (envName == null)
            envName = "local";

        return EnvironmentPropertiesMapper.load("env." + envName + ".properties");
    }

    public static ResourceConfig getRessourceConfig(EnvironmentProperties config) {
        var datastoreFactory = new DatastoreFactory(config);
        return new ResourceConfig()
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(new SellerMongodbRepository(datastoreFactory)).to(SellerRepository.class);
                        bind(new ProductMongodbRepository(datastoreFactory)).to(ProductRepository.class);
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
