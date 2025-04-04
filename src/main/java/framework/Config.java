package framework;

import config.ProjectConfig;
import org.aeonbits.owner.ConfigFactory;

public class Config {
    private static final ProjectConfig config = ConfigFactory.create(ProjectConfig.class);

    public static ProjectConfig config() {
        return config;
    }
}
