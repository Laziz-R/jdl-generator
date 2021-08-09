package com.safenetpay.task.db.command;

import com.safenetpay.task.model.Department;
import com.safenetpay.task.model.list.DepartmentList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class DepartmentCommand {
    private static Logger LOGGER = Logger.getLogger(DepartmentCommand.class);
    private PgPool client;

    public DepartmentCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> departmentAddCommand(Long loginId, Department department) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.department_add($1, $2, $3) \"department_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    department.getDepartmentName(),
                    department.getLocationId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("department_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> departmentUpdateCommand(Long loginId, Department department) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.department_update($1, $2, $3, $4) \"department_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    department.getDepartmentId(),
                    department.getDepartmentName(),
                    department.getLocationId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("department_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> departmentDeleteCommand(Long loginId, Long departmentId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.department_delete($1, $2) \"department_id\";")
            .execute(Tuple.of(loginId, departmentId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("department_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Department> departmentGetCommand(Long loginId, Long departmentId) {
        Promise<Department> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.department_get($1, $2);")
            .execute(Tuple.of(loginId, departmentId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createDepartment(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<DepartmentList> departmentGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<DepartmentList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.department_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                DepartmentList departmentList = new DepartmentList();
                res.forEach(row -> departmentList.add(createDepartment(row)));
                promise.complete(departmentList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private Department createDepartment(Row row){
        return new Department()
            .setDepartmentId(row.getLong("department_id"))
            .setDepartmentName(row.getString("department_name"))
            .setLocationId(row.getLong("location_id"))
;
    }
}