package ${package};

import ${package}.verticles.HttpServerVerticle;

import org.apache.log4j.Logger;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class App extends AbstractVerticle {
    private static Logger LOGGER = Logger.getLogger(App.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new App());
    }

    @Override
    public void start() throws Exception {
        String path = System.getProperty("user.dir") + "/src/main/resources/config.json";
        ConfigStoreOptions storeOptions = new ConfigStoreOptions()
            .setType("file")
            .setConfig(new JsonObject().put("path", path));
        ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(storeOptions);
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

        retriever.getConfig()
            .onSuccess(config->{
                vertx.deployVerticle(new HttpServerVerticle(), 
                    new DeploymentOptions().setConfig(config));
            })
            .onFailure(ar->{
                LOGGER.error("Config file...");
            });
    }
}