package com.intern.task.verticles;

import com.intern.task.common.FtlGenerator;

import org.apache.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class HttpServerVerticle extends AbstractVerticle{
    private static Logger LOGGER = Logger.getLogger(HttpServerVerticle.class);

    @Override
    public void start() throws Exception {
        String host = "localhost";      //config().getJsonObject("http").getString("host");
        int port = 8079;                //config().getJsonObject("http").getInteger("port");

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create()
            .setDeleteUploadedFilesOnEnd(true)
            .setBodyLimit(10_000));

        router.route("/*").handler(StaticHandler.create());
        
        router.post("/file-upload").handler(this::uploadHandler);
        
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(port, host)
                .onSuccess(server->{
                    LOGGER.info(String.format("Server started ...\thttp://%s:%d/", host, port));
                });
    }

    void uploadHandler(RoutingContext ctx){
        FtlGenerator ftlGenerator = new FtlGenerator(ctx);
        ftlGenerator.generate()
            .onSuccess(tree -> ctx.end(tree.encodePrettily()))
            .onFailure(ar -> ctx.end(ar.getMessage()));
    }
}
