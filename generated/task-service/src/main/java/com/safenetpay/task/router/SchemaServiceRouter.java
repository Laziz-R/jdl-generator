package com.safenetpay.task.router;

import com.safenetpay.task.controller.TaskServiceController;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import org.apache.log4j.Logger;

/**
* TaskServiceRouter class.
* @Author laziz
*
* @since 2021-08-13
*/
public class TaskServiceRouter {
    private static Logger LOGGER = Logger.getLogger(TaskServiceRouter.class);

    private Vertx vertx;
    private Router router;
    private RouterBuilder routerBuilder;
    private TaskServiceController taskServiceController;

    public TaskRouter(Vertx vertx, RouterBuilder routerBuilder) {
        LOGGER.info("init: Creating Router - start");
        this.vertx = vertx;
        this.routerBuilder = routerBuilder;
        this.taskServiceController = new TaskServiceController(vertx);
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

        // this.routerBuilder.addSecurityHandler("ApiKeyAuth", this.taskServiceController::handleApiKeyAuth);


        // region Book

        this.routerBuilder.operation("taskBookAdd")
            .handler(taskServiceController::handleBookAdd);

        this.routerBuilder.operation("taskBookUpdate")
            .handler(taskServiceController::handleBookUpdate);

        this.routerBuilder.operation("taskBookDelete")
            .handler(taskServiceController::handleBookDelete);

        this.routerBuilder.operation("taskBookGet")
            .handler(taskServiceController::handleBookGet);

        this.routerBuilder.operation("taskBookGetList")
            .handler(taskServiceController::handleBookGetList);

        this.routerBuilder.operation("taskBookGetAll")
            .handler(taskServiceController::handleBookGetAll);

        this.routerBuilder.operation("taskBookGetSummaryList")
            .handler(taskServiceController::handleBookGetSummaryList);

        // endregion


        // region Author

        this.routerBuilder.operation("taskAuthorAdd")
            .handler(taskServiceController::handleAuthorAdd);

        this.routerBuilder.operation("taskAuthorUpdate")
            .handler(taskServiceController::handleAuthorUpdate);

        this.routerBuilder.operation("taskAuthorDelete")
            .handler(taskServiceController::handleAuthorDelete);

        this.routerBuilder.operation("taskAuthorGet")
            .handler(taskServiceController::handleAuthorGet);

        this.routerBuilder.operation("taskAuthorGetList")
            .handler(taskServiceController::handleAuthorGetList);

        this.routerBuilder.operation("taskAuthorGetAll")
            .handler(taskServiceController::handleAuthorGetAll);

        this.routerBuilder.operation("taskAuthorGetSummaryList")
            .handler(taskServiceController::handleAuthorGetSummaryList);

        // endregion


        // region BookAuthor

        this.routerBuilder.operation("taskBookAuthorAdd")
            .handler(taskServiceController::handleBookAuthorAdd);

        this.routerBuilder.operation("taskBookAuthorUpdate")
            .handler(taskServiceController::handleBookAuthorUpdate);

        this.routerBuilder.operation("taskBookAuthorDelete")
            .handler(taskServiceController::handleBookAuthorDelete);

        this.routerBuilder.operation("taskBookAuthorGet")
            .handler(taskServiceController::handleBookAuthorGet);

        this.routerBuilder.operation("taskBookAuthorGetList")
            .handler(taskServiceController::handleBookAuthorGetList);

        this.routerBuilder.operation("taskBookAuthorGetAll")
            .handler(taskServiceController::handleBookAuthorGetAll);

        this.routerBuilder.operation("taskBookAuthorGetSummaryList")
            .handler(taskServiceController::handleBookAuthorGetSummaryList);

        // endregion

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
        this.router.route().failureHandler(this.taskServiceController::defaultFailureHandler);
        this.router.route().handler(this.taskServiceController::handlerNotFound);

        LOGGER.info("init: Making Router - completed");

        return this.getRouter();    
    }

    public Router getRouter(){
        return this.router;
    }
}
