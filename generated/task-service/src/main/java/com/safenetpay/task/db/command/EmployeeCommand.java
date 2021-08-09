package com.safenetpay.task.db.command;

import com.safenetpay.task.model.Employee;
import com.safenetpay.task.model.list.EmployeeList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class EmployeeCommand {
    private static Logger LOGGER = Logger.getLogger(EmployeeCommand.class);
    private PgPool client;

    public EmployeeCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> employeeAddCommand(Long loginId, Employee employee) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.employee_add($1, $2, $3, $4, $5, $6, $7, $8, $9) \"employee_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getPhoneNumber(),
                    employee.getHireDate(),
                    employee.getSalary(),
                    employee.getCommissionPct(),
                    employee.getDepartmentId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("employee_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> employeeUpdateCommand(Long loginId, Employee employee) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.employee_update($1, $2, $3, $4, $5, $6, $7, $8, $9, $10) \"employee_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    employee.getEmployeeId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getPhoneNumber(),
                    employee.getHireDate(),
                    employee.getSalary(),
                    employee.getCommissionPct(),
                    employee.getDepartmentId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("employee_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> employeeDeleteCommand(Long loginId, Long employeeId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.employee_delete($1, $2) \"employee_id\";")
            .execute(Tuple.of(loginId, employeeId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("employee_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Employee> employeeGetCommand(Long loginId, Long employeeId) {
        Promise<Employee> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.employee_get($1, $2);")
            .execute(Tuple.of(loginId, employeeId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createEmployee(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<EmployeeList> employeeGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<EmployeeList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.employee_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                EmployeeList employeeList = new EmployeeList();
                res.forEach(row -> employeeList.add(createEmployee(row)));
                promise.complete(employeeList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private Employee createEmployee(Row row){
        return new Employee()
            .setEmployeeId(row.getLong("employee_id"))
            .setFirstName(row.getString("first_name"))
            .setLastName(row.getString("last_name"))
            .setEmail(row.getString("email"))
            .setPhoneNumber(row.getString("phone_number"))
            .setHireDate(row.getLocalDate("hire_date"))
            .setSalary(row.getLong("salary"))
            .setCommissionPct(row.getLong("commission_pct"))
            .setDepartmentId(row.getLong("department_id"))
;
    }
}