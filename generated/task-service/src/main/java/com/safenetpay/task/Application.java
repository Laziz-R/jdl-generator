package com.safenetpay.task;

import com.safenetpay.task.need.ApplicationRuntimeException;
import com.safenetpay.task.need.Error;

import com.safenetpay.task.verticles.HttpServerVerticle;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import org.apache.log4j.Logger;

public class Application extends AbstractVerticle {
    private static Logger LOGGER = Logger.getLogger(Application.class);
    private final String configPath = "/src/main/resources/config.json";

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Application());
    }

    @Override
    public void start() throws Exception {
        String path = System.getProperty("user.dir") + configPath;
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
                throw new ApplicationRuntimeException("fail to load config", Error.APPLICATION);
            });
    }
}