package ${package}.verticles;

import ${package}.router.${schema.pascalCase}Router;

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
        
        ${schema.pascalCase}Router ${schema.camelCase}Router = new ${schema.pascalCase}Router(vertx);
        ${schema.camelCase}Router.create()
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
