package com.safenetpay.task.verticles;

import com.safenetpay.task.router.TaskServiceRouter;

import org.apache.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.openapi.RouterBuilder;

public class HttpServerVerticle extends AbstractVerticle{
    private static Logger LOGGER = Logger.getLogger(HttpServerVerticle.class);
    private final String swaggerPath = "src/main/resources/webroot/swagger.yaml";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        JsonObject httpConfig = config().getJsonObject("TaskService").getJsonObject("http");

        String host = httpConfig.getString("host");
        int port = httpConfig.getInteger("port");

        RouterBuilder.create(vertx, swaggerPath)
        .onSuccess(routerBuilder -> {
            vertx
                .createHttpServer()
                .requestHandler(new TaskServiceRouter(vertx, routerBuilder).createRouting())
                .listen(port, host)
                    .onSuccess(http -> {
                        LOGGER.info(String.format("Server started ...\thttp://localhost:%d/", port));
                    })
                    .onFailure(ar -> {
                        startPromise.fail(ar);
                        LOGGER.error("HTTP Server failed to start.");
                    });
        })
        .onFailure(ar -> {
            LOGGER.error(ar);
            startPromise.fail(ar);
        });
    }
}
