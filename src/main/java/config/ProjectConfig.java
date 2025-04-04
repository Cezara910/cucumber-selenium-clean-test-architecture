package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "system:properties",
        "classpath:ProjectConfig.properties",
        })
@Config.LoadPolicy(Config.LoadType.MERGE)
public interface ProjectConfig extends Config {
    String url();

    @DefaultValue("chrome")
    String browser();

    @DefaultValue("local")
    String target();

    @DefaultValue("5")
    Integer loadingTimeout();
    @DefaultValue("build")
    String outputFolder();
}
