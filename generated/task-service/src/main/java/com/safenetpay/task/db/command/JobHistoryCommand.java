package com.safenetpay.task.db.command;

import com.safenetpay.task.model.JobHistory;
import com.safenetpay.task.model.list.JobHistoryList;
import com.safenetpay.task.model.Language;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class JobHistoryCommand {
    private static Logger LOGGER = Logger.getLogger(JobHistoryCommand.class);
    private PgPool client;

    public JobHistoryCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> jobHistoryAddCommand(Long loginId, JobHistory jobHistory) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.job_history_add($1, $2, $3, $4, $5, $6, $7) \"job_history_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    jobHistory.getStartDate(),
                    jobHistory.getEndDate(),
                    jobHistory.getLanguage(),
                    jobHistory.getJobId(),
                    jobHistory.getDepartmentId(),
                    jobHistory.getEmployeeId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("job_history_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> jobHistoryUpdateCommand(Long loginId, JobHistory jobHistory) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.job_history_update($1, $2, $3, $4, $5, $6, $7, $8) \"job_history_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    jobHistory.getJobHistoryId(),
                    jobHistory.getStartDate(),
                    jobHistory.getEndDate(),
                    jobHistory.getLanguage(),
                    jobHistory.getJobId(),
                    jobHistory.getDepartmentId(),
                    jobHistory.getEmployeeId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("job_history_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> jobHistoryDeleteCommand(Long loginId, Long jobHistoryId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.job_history_delete($1, $2) \"job_history_id\";")
            .execute(Tuple.of(loginId, jobHistoryId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("job_history_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<JobHistory> jobHistoryGetCommand(Long loginId, Long jobHistoryId) {
        Promise<JobHistory> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.job_history_get($1, $2);")
            .execute(Tuple.of(loginId, jobHistoryId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createJobHistory(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<JobHistoryList> jobHistoryGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<JobHistoryList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.job_history_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                JobHistoryList jobHistoryList = new JobHistoryList();
                res.forEach(row -> jobHistoryList.add(createJobHistory(row)));
                promise.complete(jobHistoryList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private JobHistory createJobHistory(Row row){
        return new JobHistory()
            .setJobHistoryId(row.getLong("job_history_id"))
            .setStartDate(row.getLocalDate("start_date"))
            .setEndDate(row.getLocalDate("end_date"))
            .setLanguage(Language.valueOf(row.getString("language")))
            .setJobId(row.getLong("job_id"))
            .setDepartmentId(row.getLong("department_id"))
            .setEmployeeId(row.getLong("employee_id"))
;
    }
}