package com.safenetpay.task.router;

import com.safenetpay.task.controller.TaskController;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import org.apache.log4j.Logger;

public class TaskRouter {
    private static Logger LOGGER = Logger.getLogger(TaskRouter.class);
    private final String swaggerPath = "src/main/resources/webroot/swagger.yaml";

    private Vertx vertx;
    private TaskController taskController;

    public TaskRouter(Vertx vertx) {
        this.vertx = vertx;
        this.taskController = new TaskController(vertx);
    }

    public Future<Router> create(){
        Promise<Router> promise = Promise.promise();
        RouterBuilder.create(vertx, swaggerPath)
            .onSuccess(builder -> {

                // region Book

                builder.operation("taskBookAdd")
                    .handler(taskController::handleBookAdd);

                builder.operation("taskBookUpdate")
                    .handler(taskController::handleBookUpdate);

                builder.operation("taskBookDelete")
                    .handler(taskController::handleBookDelete);

                builder.operation("taskBookGet")
                    .handler(taskController::handleBookGet);

                builder.operation("taskBookGetList")
                    .handler(taskController::handleBookGetList);

                // endregion


                // region Author

                builder.operation("taskAuthorAdd")
                    .handler(taskController::handleAuthorAdd);

                builder.operation("taskAuthorUpdate")
                    .handler(taskController::handleAuthorUpdate);

                builder.operation("taskAuthorDelete")
                    .handler(taskController::handleAuthorDelete);

                builder.operation("taskAuthorGet")
                    .handler(taskController::handleAuthorGet);

                builder.operation("taskAuthorGetList")
                    .handler(taskController::handleAuthorGetList);

                // endregion


                // region BookAuthor

                builder.operation("taskBookAuthorAdd")
                    .handler(taskController::handleBookAuthorAdd);

                builder.operation("taskBookAuthorUpdate")
                    .handler(taskController::handleBookAuthorUpdate);

                builder.operation("taskBookAuthorDelete")
                    .handler(taskController::handleBookAuthorDelete);

                builder.operation("taskBookAuthorGet")
                    .handler(taskController::handleBookAuthorGet);

                builder.operation("taskBookAuthorGetList")
                    .handler(taskController::handleBookAuthorGetList);

                // endregion

                builder.bodyHandler(BodyHandler.create());
                Router router = builder.createRouter();
                router.route().handler(StaticHandler.create());

                promise.complete(router);
            })
            .onFailure(promise::fail);
        return promise.future();
    }
}
