package ulaval.glo2003.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.conversions.Bson;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    private static final String DB_HOST = "127.0.0.1";
    private static final int DB_PORT = 27017;
    private static final String DB_NAME = "morphia";
    private static final String DB_USER = "morphia";
    private static final String DB_PASSWORD = "morphia";
    private MongoDatabase database;
    private Datastore datastore;


    public MongoDB(){
        try (MongoClient mongoClient = MongoClients.create(getClientSetting())) {
            database = mongoClient.getDatabase("Admin");
            setupDataStore();
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                Document commandResult = database.runCommand(command);
                System.out.println("Connected successfully to server.");
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }

    private void setupDataStore(){
        datastore = Morphia.createDatastore(MongoClients.create(getClientSetting()), "Floppa");

        datastore.getMapper().mapPackage("ulaval.glo2003.seller");
        datastore.getMapper().mapPackage("ulaval.glo2003.product");
        datastore.getMapper().mapPackage("ulaval.glo2003.offer");

        datastore.getDatabase().drop();
        datastore.ensureIndexes();
    }

    public Datastore getDatastore(){
        return datastore;
    }

    public MongoDatabase getDatabase(){
        return database;
    }

    private MongoClientSettings getClientSetting(){
        var uri = "mongodb://" + DB_HOST + ":" + DB_PORT;
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
        return clientSettings;
    }
}
