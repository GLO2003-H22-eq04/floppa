package ulaval.glo2003.domain.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvironmentPropertiesMapper {
    public static EnvironmentProperties load(String filename) {
        try (InputStream input = EnvironmentPropertiesMapper.class.getResourceAsStream("/" + filename)) {
            Properties prop = new Properties();
            prop.load(input);

            return new EnvironmentProperties(prop.getProperty("mongoName"),
                    prop.getProperty("mongoConnectionString"),
                    prop.getProperty("apiBaseUrl"));
        } catch (IOException io) {
            io.printStackTrace();
            throw new RuntimeException(io);
        }
    }
}
