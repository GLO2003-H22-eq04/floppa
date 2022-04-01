package ulaval.glo2003.domain.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ConfigMongodb {
    private static final String DB_HOST = "127.0.0.1";
    private static final int DB_PORT = 27017;
    private static final String DB_NAME = "morphia";
    private static final String DB_USER = "morphia";
    private static final String DB_PASSWORD = "morphia";
    private MongoDatabase database;
    private Datastore datastore;


    public ConfigMongodb() {
        try (MongoClient mongoClient = MongoClients.create(getClientSetting())) {
            database = mongoClient.getDatabase("Admin");
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println("Connected successfully to server.");
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }

    public MongoClientSettings getClientSetting() {
        var uri = "mongodb://" + DB_HOST + ":" + DB_PORT;

        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyToSocketSettings(builder ->
                        builder.connectTimeout(1, SECONDS))
                .build();
    }
}
