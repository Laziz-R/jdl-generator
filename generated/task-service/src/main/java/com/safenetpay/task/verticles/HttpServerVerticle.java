package com.safenetpay.task.verticles;

import com.safenetpay.task.router.TaskRouter;

import org.apache.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class HttpServerVerticle extends AbstractVerticle{
    private static Logger LOGGER = Logger.getLogger(HttpServerVerticle.class);

    @Override
    public void start() throws Exception {
        JsonObject httpConfig = config().getJsonObject("http");

        String host = httpConfig.getString("host");
        int port = httpConfig.getInteger("port");
        
        TaskRouter taskRouter = new TaskRouter(vertx);
        taskRouter.create()
            .onSuccess(router ->
                vertx.createHttpServer()
                    .requestHandler(router)
                    .listen(port, host)
                        .onSuccess(server->{
                            LOGGER.info(String.format("Server started ...\thttp://%s:%d/", host, port));
                        }))
            .onFailure(ar -> LOGGER.error(ar));
    }
}
