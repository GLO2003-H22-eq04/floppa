package ulaval.glo2003.domain.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class DatastoreFactory {
    private Datastore datastore;

    public DatastoreFactory(ConfigMongodb clientSettings) {
        this.datastore = createDatastore(clientSettings.getClientSetting());
    }

    private Datastore createDatastore(MongoClientSettings clientSettings){
        datastore = Morphia.createDatastore(MongoClients.create(clientSettings), "Floppa");

        datastore.getMapper().mapPackage("ulaval.glo2003.seller");
        datastore.getMapper().mapPackage("ulaval.glo2003.product");
        datastore.getMapper().mapPackage("ulaval.glo2003.offer");

        datastore.getDatabase().drop();
        datastore.ensureIndexes();
        return datastore;
    }

    public Datastore getDatastore(){
        return datastore;
    }
}
