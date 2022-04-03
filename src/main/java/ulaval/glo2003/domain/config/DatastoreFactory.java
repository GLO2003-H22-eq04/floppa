package ulaval.glo2003.domain.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bson.UuidRepresentation;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DatastoreFactory {
    private Datastore datastore;

    public DatastoreFactory(EnvironmentProperties config) {
        this.datastore = createDatastore(createClientSettings(config), config.mongoDbName);
    }

    private MongoClientSettings createClientSettings(EnvironmentProperties config) {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(config.mongoConnectionString))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyToSocketSettings(builder ->
                        builder.connectTimeout(1, SECONDS))
                .build();
    }

    private Datastore createDatastore(MongoClientSettings clientSettings, String dbName) {
        datastore = Morphia.createDatastore(MongoClients.create(clientSettings), dbName);

        datastore.getMapper().mapPackage("ulaval.glo2003.seller");
        datastore.getMapper().mapPackage("ulaval.glo2003.product");
        datastore.getMapper().mapPackage("ulaval.glo2003.offer");
        datastore.ensureIndexes();
        return datastore;
    }

    public Datastore getDatastore() {
        return datastore;
    }
}
