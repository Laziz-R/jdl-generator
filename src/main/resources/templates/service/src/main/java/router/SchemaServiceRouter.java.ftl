<#assign aDate = .now>
<#assign schemaCamel = schema.camelCase/>
<#assign schemaPascal = schema.pascalCase/>
package ${package}.router;

import ${package}.controller.${schemaPascal}ServiceController;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import org.apache.log4j.Logger;

/**
* ${schemaPascal}ServiceRouter class.
* @Author ${(author)!""}
*
* @since ${aDate?date?iso_utc}
*/
public class ${schemaPascal}ServiceRouter {
    private static Logger LOGGER = Logger.getLogger(${schemaPascal}ServiceRouter.class);

    private Vertx vertx;
    private Router router;
    private RouterBuilder routerBuilder;
    private ${schemaPascal}ServiceController ${schemaCamel}ServiceController;

    public ${schemaPascal}Router(Vertx vertx, RouterBuilder routerBuilder) {
        LOGGER.info("init: Creating Router - start");
        this.vertx = vertx;
        this.routerBuilder = routerBuilder;
        this.${schemaCamel}ServiceController = new ${schemaPascal}ServiceController(vertx);
        LOGGER.info("init: Creating Router - completed");
    }

    /**
    * Create route class with bind handlers.
    *
    * @return - this
    */
    public Router createRouting(){

        // map methods here
        
        this.routerBuilder.setBodyHandler(BodyHandler.create());

        // this.routerBuilder.addSecurityHandler("ApiKeyAuth", this.${schemaCamel}ServiceController::handleApiKeyAuth);

        <#list entities as entity>
        <#assign entityPascal = entity.name.pascalCase/>

        // region ${entityPascal}

        this.routerBuilder.operation("${schemaCamel}${entityPascal}Add")
            .handler(${schemaCamel}ServiceController::handle${entityPascal}Add);

        this.routerBuilder.operation("${schemaCamel}${entityPascal}Update")
            .handler(${schemaCamel}ServiceController::handle${entityPascal}Update);

        this.routerBuilder.operation("${schemaCamel}${entityPascal}Delete")
            .handler(${schemaCamel}ServiceController::handle${entityPascal}Delete);

        this.routerBuilder.operation("${schemaCamel}${entityPascal}Get")
            .handler(${schemaCamel}ServiceController::handle${entityPascal}Get);

        this.routerBuilder.operation("${schemaCamel}${entityPascal}GetList")
            .handler(${schemaCamel}ServiceController::handle${entityPascal}GetList);

        // endregion

        </#list>
        // base methods

        this.router = this.routerBuilder.createRouter();

        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("Access-Control-Allow-Method");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("Access-Control-Allow-Credentials");
        allowedHeaders.add("Access-Control-Allow-Headers");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("token");

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);

        this.router
            .route()
            .handler(CorsHandler.create(".*.")
                .allowCredentials(true)
                .allowedMethods(allowedMethods)
                .allowedHeaders(allowedHeaders));
        this.router.route().handler(StaticHandler.create().setCachingEnabled(false));
        this.router.route().failureHandler(this.${schemaCamel}ServiceController::defaultFailureHandler);
        this.router.route().handler(this.${schemaCamel}ServiceController::handlerNotFound);

        LOGGER.info("init: Making Router - completed");

        return this.getRouter();    
    }

    public Router getRouter(){
        return this.router;
    }
}
