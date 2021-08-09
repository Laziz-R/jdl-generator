package com.safenetpay.task.db.command;

import com.safenetpay.task.model.JobTask;
import com.safenetpay.task.model.list.JobTaskList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class JobTaskCommand {
    private static Logger LOGGER = Logger.getLogger(JobTaskCommand.class);
    private PgPool client;

    public JobTaskCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> jobTaskAddCommand(Long loginId, JobTask jobTask) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.job_task_add($1, $2, $3) \"job_task_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    jobTask.getJobTitle(),
                    jobTask.getTitle()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("job_task_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> jobTaskUpdateCommand(Long loginId, JobTask jobTask) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.job_task_update($1, $2, $3, $4) \"job_task_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    jobTask.getJobTaskId(),
                    jobTask.getJobTitle(),
                    jobTask.getTitle()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("job_task_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> jobTaskDeleteCommand(Long loginId, Long jobTaskId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.job_task_delete($1, $2) \"job_task_id\";")
            .execute(Tuple.of(loginId, jobTaskId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("job_task_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<JobTask> jobTaskGetCommand(Long loginId, Long jobTaskId) {
        Promise<JobTask> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.job_task_get($1, $2);")
            .execute(Tuple.of(loginId, jobTaskId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createJobTask(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<JobTaskList> jobTaskGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<JobTaskList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.job_task_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                JobTaskList jobTaskList = new JobTaskList();
                res.forEach(row -> jobTaskList.add(createJobTask(row)));
                promise.complete(jobTaskList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private JobTask createJobTask(Row row){
        return new JobTask()
            .setJobTaskId(row.getLong("job_task_id"))
            .setJobTitle(row.getString("job_title"))
            .setTitle(row.getString("title"))
;
    }
}