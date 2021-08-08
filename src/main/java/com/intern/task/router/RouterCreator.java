package com.intern.task.router;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.pgclient.PgPool;

public class RouterCreator {

    private Vertx vertx;
    PgPool client;

    public RouterCreator(Vertx vertx){
        this.vertx = vertx;
    }

    public Future<Router> create(){
        Promise<Router> promise = Promise.promise();
        RouterBuilder.create(vertx, "src/main/resources/webroot/swagger.yaml")
            .onSuccess(builder -> {
                builder.operation("operationId");
                builder.bodyHandler(BodyHandler.create());
                Router router = builder.createRouter();
                router.route().handler(StaticHandler.create());
            })
            .onFailure(promise::fail);
        ConfigRetriever.create(vertx)
        .getConfig().onSuccess(conf->{
            client = null;
        });
        return promise.future();
    }
}
