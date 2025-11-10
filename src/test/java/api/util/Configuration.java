package api.util;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/application.properties"})
public interface Configuration extends Config {

    @Key("base.url")
    @DefaultValue("http://localhost:8080")
    String getUrl();

    @Key("api.path")
    @DefaultValue("/api")
    String getPath();
}