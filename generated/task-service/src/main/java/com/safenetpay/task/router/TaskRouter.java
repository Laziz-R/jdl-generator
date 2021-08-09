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

    private Vertx vertx;
    private TaskController taskController;

    public TaskRouter(Vertx vertx) {
        this.vertx = vertx;
        this.taskController = new TaskController(vertx);
    }

    public Future<Router> create(){
        Promise<Router> promise = Promise.promise();
        RouterBuilder.create(vertx, "src/main/resources/webroot/swagger.yaml")
            .onSuccess(builder -> {

                // region Region

                builder.operation("taskRegionAdd")
                    .handler(taskController::handleRegionAdd);

                builder.operation("taskRegionUpdate")
                    .handler(taskController::handleRegionUpdate);

                builder.operation("taskRegionDelete")
                    .handler(taskController::handleRegionDelete);

                builder.operation("taskRegionGet")
                    .handler(taskController::handleRegionGet);

                builder.operation("taskRegionGetList")
                    .handler(taskController::handleRegionGetList);

                // endregion


                // region Country

                builder.operation("taskCountryAdd")
                    .handler(taskController::handleCountryAdd);

                builder.operation("taskCountryUpdate")
                    .handler(taskController::handleCountryUpdate);

                builder.operation("taskCountryDelete")
                    .handler(taskController::handleCountryDelete);

                builder.operation("taskCountryGet")
                    .handler(taskController::handleCountryGet);

                builder.operation("taskCountryGetList")
                    .handler(taskController::handleCountryGetList);

                // endregion


                // region Location

                builder.operation("taskLocationAdd")
                    .handler(taskController::handleLocationAdd);

                builder.operation("taskLocationUpdate")
                    .handler(taskController::handleLocationUpdate);

                builder.operation("taskLocationDelete")
                    .handler(taskController::handleLocationDelete);

                builder.operation("taskLocationGet")
                    .handler(taskController::handleLocationGet);

                builder.operation("taskLocationGetList")
                    .handler(taskController::handleLocationGetList);

                // endregion


                // region Department

                builder.operation("taskDepartmentAdd")
                    .handler(taskController::handleDepartmentAdd);

                builder.operation("taskDepartmentUpdate")
                    .handler(taskController::handleDepartmentUpdate);

                builder.operation("taskDepartmentDelete")
                    .handler(taskController::handleDepartmentDelete);

                builder.operation("taskDepartmentGet")
                    .handler(taskController::handleDepartmentGet);

                builder.operation("taskDepartmentGetList")
                    .handler(taskController::handleDepartmentGetList);

                // endregion


                // region Task

                builder.operation("taskTaskAdd")
                    .handler(taskController::handleTaskAdd);

                builder.operation("taskTaskUpdate")
                    .handler(taskController::handleTaskUpdate);

                builder.operation("taskTaskDelete")
                    .handler(taskController::handleTaskDelete);

                builder.operation("taskTaskGet")
                    .handler(taskController::handleTaskGet);

                builder.operation("taskTaskGetList")
                    .handler(taskController::handleTaskGetList);

                // endregion


                // region Employee

                builder.operation("taskEmployeeAdd")
                    .handler(taskController::handleEmployeeAdd);

                builder.operation("taskEmployeeUpdate")
                    .handler(taskController::handleEmployeeUpdate);

                builder.operation("taskEmployeeDelete")
                    .handler(taskController::handleEmployeeDelete);

                builder.operation("taskEmployeeGet")
                    .handler(taskController::handleEmployeeGet);

                builder.operation("taskEmployeeGetList")
                    .handler(taskController::handleEmployeeGetList);

                // endregion


                // region Job

                builder.operation("taskJobAdd")
                    .handler(taskController::handleJobAdd);

                builder.operation("taskJobUpdate")
                    .handler(taskController::handleJobUpdate);

                builder.operation("taskJobDelete")
                    .handler(taskController::handleJobDelete);

                builder.operation("taskJobGet")
                    .handler(taskController::handleJobGet);

                builder.operation("taskJobGetList")
                    .handler(taskController::handleJobGetList);

                // endregion


                // region JobHistory

                builder.operation("taskJobHistoryAdd")
                    .handler(taskController::handleJobHistoryAdd);

                builder.operation("taskJobHistoryUpdate")
                    .handler(taskController::handleJobHistoryUpdate);

                builder.operation("taskJobHistoryDelete")
                    .handler(taskController::handleJobHistoryDelete);

                builder.operation("taskJobHistoryGet")
                    .handler(taskController::handleJobHistoryGet);

                builder.operation("taskJobHistoryGetList")
                    .handler(taskController::handleJobHistoryGetList);

                // endregion


                // region JobTask

                builder.operation("taskJobTaskAdd")
                    .handler(taskController::handleJobTaskAdd);

                builder.operation("taskJobTaskUpdate")
                    .handler(taskController::handleJobTaskUpdate);

                builder.operation("taskJobTaskDelete")
                    .handler(taskController::handleJobTaskDelete);

                builder.operation("taskJobTaskGet")
                    .handler(taskController::handleJobTaskGet);

                builder.operation("taskJobTaskGetList")
                    .handler(taskController::handleJobTaskGetList);

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
