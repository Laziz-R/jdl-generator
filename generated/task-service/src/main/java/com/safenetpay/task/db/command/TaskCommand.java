package com.safenetpay.task.db.command;

import com.safenetpay.task.model.Task;
import com.safenetpay.task.model.list.TaskList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class TaskCommand {
    private static Logger LOGGER = Logger.getLogger(TaskCommand.class);
    private PgPool client;

    public TaskCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> taskAddCommand(Long loginId, Task task) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.task_add($1, $2, $3) \"task_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    task.getTitle(),
                    task.getDescription()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("task_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> taskUpdateCommand(Long loginId, Task task) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.task_update($1, $2, $3, $4) \"task_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    task.getTaskId(),
                    task.getTitle(),
                    task.getDescription()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("task_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> taskDeleteCommand(Long loginId, Long taskId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.task_delete($1, $2) \"task_id\";")
            .execute(Tuple.of(loginId, taskId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("task_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Task> taskGetCommand(Long loginId, Long taskId) {
        Promise<Task> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.task_get($1, $2);")
            .execute(Tuple.of(loginId, taskId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createTask(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<TaskList> taskGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<TaskList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.task_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                TaskList taskList = new TaskList();
                res.forEach(row -> taskList.add(createTask(row)));
                promise.complete(taskList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private Task createTask(Row row){
        return new Task()
            .setTaskId(row.getLong("task_id"))
            .setTitle(row.getString("title"))
            .setDescription(row.getString("description"))
;
    }
}