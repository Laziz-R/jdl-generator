package com.intern.task.verticles;

import org.apache.log4j.Logger;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

public class StartVerticle extends AbstractVerticle{
    private static Logger LOGGER = Logger.getLogger(StartVerticle.class);
    
    @Override
    public void start() throws Exception {
        String path = System.getProperty("user.dir") + "/src/main/resources/config/config.json";
        ConfigStoreOptions storeOptions = new ConfigStoreOptions()
            .setType("file")
            .setConfig(new JsonObject().put("path", path));
        ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(storeOptions);
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

        retriever.getConfig()
            .onSuccess(conf->{
                vertx.deployVerticle(new HttpServerVerticle(), 
                    new DeploymentOptions().setConfig(conf));
            })
            .onFailure(ar->{
                LOGGER.error("Config file...");
            });
    }
}
