package ulaval.glo2003.domain.config;

import java.io.IOException;
import java.util.Properties;

public class EnvironmentPropertiesMapper {
    public static EnvironmentProperties load(String filename) {
        try (var input = EnvironmentPropertiesMapper.class.getResourceAsStream("/" + filename)) {
            var prop = new Properties();
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
