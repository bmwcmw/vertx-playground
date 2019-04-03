import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import java.util.Properties;

public class Module extends AbstractModule {

    private final Vertx vertx;
    private final Context context;

    public Module(Vertx vertx) {
        this.vertx = vertx;
        this.context = vertx.getOrCreateContext();
    }

    @Override
    protected void configure() {
        bind(EventBus.class).toInstance(vertx.eventBus());
        Names.bindProperties(binder(),
                extractToProperties(context.config()));
    }

    private Properties extractToProperties(JsonObject config) {
        Properties properties = new Properties();
        config.getMap().keySet().forEach((String key) -> {
            properties.setProperty(key, (String) config.getValue(key));
        });
        return properties;
    }
}