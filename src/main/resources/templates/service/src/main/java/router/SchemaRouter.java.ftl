package ${package}.router;

import ${package}.controller.${schema.pascalCase}Controller;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import org.apache.log4j.Logger;

public class ${schema.pascalCase}Router {
<#assign pre = schema.camelCase/>
    private static Logger LOGGER = Logger.getLogger(${schema.pascalCase}Router.class);

    private Vertx vertx;
    private ${schema.pascalCase}Controller ${pre}Controller;

    public ${schema.pascalCase}Router(Vertx vertx) {
        this.vertx = vertx;
        this.${pre}Controller = new ${schema.pascalCase}Controller(vertx);
    }

    public Future<Router> create(){
        Promise<Router> promise = Promise.promise();
        RouterBuilder.create(vertx, "src/main/resources/webroot/swagger.yaml")
            .onSuccess(builder -> {
                <#list entities as entity>
                <#assign pascalName = entity.name.pascalCase/>

                // region ${pascalName}

                builder.operation("${pre}${pascalName}Add")
                    .handler(${pre}Controller::handle${pascalName}Add);

                builder.operation("${pre}${pascalName}Update")
                    .handler(${pre}Controller::handle${pascalName}Update);

                builder.operation("${pre}${pascalName}Delete")
                    .handler(${pre}Controller::handle${pascalName}Delete);

                builder.operation("${pre}${pascalName}Get")
                    .handler(${pre}Controller::handle${pascalName}Get);

                builder.operation("${pre}${pascalName}GetList")
                    .handler(${pre}Controller::handle${pascalName}GetList);

                // endregion

                </#list>
                builder.bodyHandler(BodyHandler.create());
                Router router = builder.createRouter();
                router.route().handler(StaticHandler.create());

                promise.complete(router);
            })
            .onFailure(promise::fail);
        return promise.future();
    }
}
