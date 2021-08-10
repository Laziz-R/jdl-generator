<#assign schemaCamel = schema.camelCase/>
<#assign schemaPascal = schema.pascalCase/>
package ${package}.router;

import ${package}.controller.${schemaPascal}Controller;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import org.apache.log4j.Logger;

public class ${schemaPascal}Router {
    private static Logger LOGGER = Logger.getLogger(${schemaPascal}Router.class);
    private final String swaggerPath = "src/main/resources/webroot/swagger.yaml";

    private Vertx vertx;
    private ${schemaPascal}Controller ${schemaCamel}Controller;

    public ${schemaPascal}Router(Vertx vertx) {
        this.vertx = vertx;
        this.${schemaCamel}Controller = new ${schemaPascal}Controller(vertx);
    }

    public Future<Router> create(){
        Promise<Router> promise = Promise.promise();
        RouterBuilder.create(vertx, swaggerPath)
            .onSuccess(builder -> {
                <#list entities as entity>
                <#assign pascalName = entity.name.pascalCase/>

                // region ${pascalName}

                builder.operation("${schemaCamel}${pascalName}Add")
                    .handler(${schemaCamel}Controller::handle${pascalName}Add);

                builder.operation("${schemaCamel}${pascalName}Update")
                    .handler(${schemaCamel}Controller::handle${pascalName}Update);

                builder.operation("${schemaCamel}${pascalName}Delete")
                    .handler(${schemaCamel}Controller::handle${pascalName}Delete);

                builder.operation("${schemaCamel}${pascalName}Get")
                    .handler(${schemaCamel}Controller::handle${pascalName}Get);

                builder.operation("${schemaCamel}${pascalName}GetList")
                    .handler(${schemaCamel}Controller::handle${pascalName}GetList);

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
