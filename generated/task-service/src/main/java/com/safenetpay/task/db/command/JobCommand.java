package com.safenetpay.task.db.command;

import com.safenetpay.task.model.Job;
import com.safenetpay.task.model.list.JobList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class JobCommand {
    private static Logger LOGGER = Logger.getLogger(JobCommand.class);
    private PgPool client;

    public JobCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> jobAddCommand(Long loginId, Job job) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.job_add($1, $2, $3, $4, $5) \"job_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    job.getJobTitle(),
                    job.getMinSalary(),
                    job.getMaxSalary(),
                    job.getEmployeeId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("job_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> jobUpdateCommand(Long loginId, Job job) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.job_update($1, $2, $3, $4, $5, $6) \"job_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    job.getJobId(),
                    job.getJobTitle(),
                    job.getMinSalary(),
                    job.getMaxSalary(),
                    job.getEmployeeId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("job_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> jobDeleteCommand(Long loginId, Long jobId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.job_delete($1, $2) \"job_id\";")
            .execute(Tuple.of(loginId, jobId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("job_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Job> jobGetCommand(Long loginId, Long jobId) {
        Promise<Job> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.job_get($1, $2);")
            .execute(Tuple.of(loginId, jobId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createJob(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<JobList> jobGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<JobList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.job_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                JobList jobList = new JobList();
                res.forEach(row -> jobList.add(createJob(row)));
                promise.complete(jobList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private Job createJob(Row row){
        return new Job()
            .setJobId(row.getLong("job_id"))
            .setJobTitle(row.getString("job_title"))
            .setMinSalary(row.getLong("min_salary"))
            .setMaxSalary(row.getLong("max_salary"))
            .setEmployeeId(row.getLong("employee_id"))
;
    }
}