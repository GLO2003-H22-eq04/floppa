package ulaval.glo2003.domain.config;

public class EnvironmentProperties {
    public final String mongoDbName;
    public final String mongoConnectionString;
    public final String apiBaseUrl;

    public EnvironmentProperties(String mongoDbName, String mongoConnectionString, String apiBaseUrl) {
        this.mongoDbName = mongoDbName;
        this.mongoConnectionString = mongoConnectionString;
        this.apiBaseUrl = apiBaseUrl;
    }
}
