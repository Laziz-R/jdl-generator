package com.safenetpay.task.db;

import com.safenetpay.task.db.command.RegionCommand;
import com.safenetpay.task.db.command.CountryCommand;
import com.safenetpay.task.db.command.LocationCommand;
import com.safenetpay.task.db.command.DepartmentCommand;
import com.safenetpay.task.db.command.TaskCommand;
import com.safenetpay.task.db.command.EmployeeCommand;
import com.safenetpay.task.db.command.JobCommand;
import com.safenetpay.task.db.command.JobHistoryCommand;
import com.safenetpay.task.db.command.JobTaskCommand;

import com.safenetpay.task.model.Region;
import com.safenetpay.task.model.list.RegionList;
import com.safenetpay.task.model.Country;
import com.safenetpay.task.model.list.CountryList;
import com.safenetpay.task.model.Location;
import com.safenetpay.task.model.list.LocationList;
import com.safenetpay.task.model.Department;
import com.safenetpay.task.model.list.DepartmentList;
import com.safenetpay.task.model.Task;
import com.safenetpay.task.model.list.TaskList;
import com.safenetpay.task.model.Employee;
import com.safenetpay.task.model.list.EmployeeList;
import com.safenetpay.task.model.Job;
import com.safenetpay.task.model.list.JobList;
import com.safenetpay.task.model.JobHistory;
import com.safenetpay.task.model.list.JobHistoryList;
import com.safenetpay.task.model.JobTask;
import com.safenetpay.task.model.list.JobTaskList;
import com.safenetpay.task.need.UserCredentials;

import java.util.List;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

import org.apache.log4j.Logger;

public class TaskService {
    private static Logger LOGGER = Logger.getLogger(TaskService.class);

    private RegionCommand regionCommand; 
    private CountryCommand countryCommand; 
    private LocationCommand locationCommand; 
    private DepartmentCommand departmentCommand; 
    private TaskCommand taskCommand; 
    private EmployeeCommand employeeCommand; 
    private JobCommand jobCommand; 
    private JobHistoryCommand jobHistoryCommand; 
    private JobTaskCommand jobTaskCommand; 
    private Vertx vertx;

    public TaskService (Vertx vertx){
        this.vertx = vertx;
        JsonObject dbConfig = vertx.getOrCreateContext().config().getJsonObject("db");
        PgConnectOptions connectOptions = new PgConnectOptions()
            .setPort(dbConfig.getInteger("port"))
            .setHost(dbConfig.getString("host"))
            .setDatabase(dbConfig.getString("name"))
            .setUser(dbConfig.getString("user"))
            .setPassword(dbConfig.getString("pass"));
            
        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
        PgPool client = PgPool.pool(vertx, connectOptions, poolOptions);
        this.regionCommand = new RegionCommand(client); 
        this.countryCommand = new CountryCommand(client); 
        this.locationCommand = new LocationCommand(client); 
        this.departmentCommand = new DepartmentCommand(client); 
        this.taskCommand = new TaskCommand(client); 
        this.employeeCommand = new EmployeeCommand(client); 
        this.jobCommand = new JobCommand(client); 
        this.jobHistoryCommand = new JobHistoryCommand(client); 
        this.jobTaskCommand = new JobTaskCommand(client); 
    }

    //region Region

    public Future<Long> regionAdd(UserCredentials uc, Region region) {
        return this.regionCommand
            .regionAddCommand(uc.getLoginId(), region);
    }
    
    public Future<Long> regionUpdate(UserCredentials uc, Region region) {
        return this.regionCommand
            .regionUpdateCommand(uc.getLoginId(), region);
    }

    public Future<Long> regionDelete(UserCredentials uc, Long regionId) {
        return this.regionCommand
            .regionDeleteCommand(uc.getLoginId(), regionId);
    }

    public Future<Region> regionGet(UserCredentials uc, Long regionId) {
        return this.regionCommand
            .regionGetCommand(uc.getLoginId(), regionId);
    }

    public Future<RegionList> regionGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.regionCommand
            .regionGetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    

    //region Country

    public Future<Long> countryAdd(UserCredentials uc, Country country) {
        return this.countryCommand
            .countryAddCommand(uc.getLoginId(), country);
    }
    
    public Future<Long> countryUpdate(UserCredentials uc, Country country) {
        return this.countryCommand
            .countryUpdateCommand(uc.getLoginId(), country);
    }

    public Future<Long> countryDelete(UserCredentials uc, Long countryId) {
        return this.countryCommand
            .countryDeleteCommand(uc.getLoginId(), countryId);
    }

    public Future<Country> countryGet(UserCredentials uc, Long countryId) {
        return this.countryCommand
            .countryGetCommand(uc.getLoginId(), countryId);
    }

    public Future<CountryList> countryGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.countryCommand
            .countryGetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    

    //region Location

    public Future<Long> locationAdd(UserCredentials uc, Location location) {
        return this.locationCommand
            .locationAddCommand(uc.getLoginId(), location);
    }
    
    public Future<Long> locationUpdate(UserCredentials uc, Location location) {
        return this.locationCommand
            .locationUpdateCommand(uc.getLoginId(), location);
    }

    public Future<Long> locationDelete(UserCredentials uc, Long locationId) {
        return this.locationCommand
            .locationDeleteCommand(uc.getLoginId(), locationId);
    }

    public Future<Location> locationGet(UserCredentials uc, Long locationId) {
        return this.locationCommand
            .locationGetCommand(uc.getLoginId(), locationId);
    }

    public Future<LocationList> locationGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.locationCommand
            .locationGetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    

    //region Department

    public Future<Long> departmentAdd(UserCredentials uc, Department department) {
        return this.departmentCommand
            .departmentAddCommand(uc.getLoginId(), department);
    }
    
    public Future<Long> departmentUpdate(UserCredentials uc, Department department) {
        return this.departmentCommand
            .departmentUpdateCommand(uc.getLoginId(), department);
    }

    public Future<Long> departmentDelete(UserCredentials uc, Long departmentId) {
        return this.departmentCommand
            .departmentDeleteCommand(uc.getLoginId(), departmentId);
    }

    public Future<Department> departmentGet(UserCredentials uc, Long departmentId) {
        return this.departmentCommand
            .departmentGetCommand(uc.getLoginId(), departmentId);
    }

    public Future<DepartmentList> departmentGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.departmentCommand
            .departmentGetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    

    //region Task

    public Future<Long> taskAdd(UserCredentials uc, Task task) {
        return this.taskCommand
            .taskAddCommand(uc.getLoginId(), task);
    }
    
    public Future<Long> taskUpdate(UserCredentials uc, Task task) {
        return this.taskCommand
            .taskUpdateCommand(uc.getLoginId(), task);
    }

    public Future<Long> taskDelete(UserCredentials uc, Long taskId) {
        return this.taskCommand
            .taskDeleteCommand(uc.getLoginId(), taskId);
    }

    public Future<Task> taskGet(UserCredentials uc, Long taskId) {
        return this.taskCommand
            .taskGetCommand(uc.getLoginId(), taskId);
    }

    public Future<TaskList> taskGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.taskCommand
            .taskGetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    

    //region Employee

    public Future<Long> employeeAdd(UserCredentials uc, Employee employee) {
        return this.employeeCommand
            .employeeAddCommand(uc.getLoginId(), employee);
    }
    
    public Future<Long> employeeUpdate(UserCredentials uc, Employee employee) {
        return this.employeeCommand
            .employeeUpdateCommand(uc.getLoginId(), employee);
    }

    public Future<Long> employeeDelete(UserCredentials uc, Long employeeId) {
        return this.employeeCommand
            .employeeDeleteCommand(uc.getLoginId(), employeeId);
    }

    public Future<Employee> employeeGet(UserCredentials uc, Long employeeId) {
        return this.employeeCommand
            .employeeGetCommand(uc.getLoginId(), employeeId);
    }

    public Future<EmployeeList> employeeGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.employeeCommand
            .employeeGetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    

    //region Job

    public Future<Long> jobAdd(UserCredentials uc, Job job) {
        return this.jobCommand
            .jobAddCommand(uc.getLoginId(), job);
    }
    
    public Future<Long> jobUpdate(UserCredentials uc, Job job) {
        return this.jobCommand
            .jobUpdateCommand(uc.getLoginId(), job);
    }

    public Future<Long> jobDelete(UserCredentials uc, Long jobId) {
        return this.jobCommand
            .jobDeleteCommand(uc.getLoginId(), jobId);
    }

    public Future<Job> jobGet(UserCredentials uc, Long jobId) {
        return this.jobCommand
            .jobGetCommand(uc.getLoginId(), jobId);
    }

    public Future<JobList> jobGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.jobCommand
            .jobGetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    

    //region JobHistory

    public Future<Long> jobHistoryAdd(UserCredentials uc, JobHistory jobHistory) {
        return this.jobHistoryCommand
            .jobHistoryAddCommand(uc.getLoginId(), jobHistory);
    }
    
    public Future<Long> jobHistoryUpdate(UserCredentials uc, JobHistory jobHistory) {
        return this.jobHistoryCommand
            .jobHistoryUpdateCommand(uc.getLoginId(), jobHistory);
    }

    public Future<Long> jobHistoryDelete(UserCredentials uc, Long jobHistoryId) {
        return this.jobHistoryCommand
            .jobHistoryDeleteCommand(uc.getLoginId(), jobHistoryId);
    }

    public Future<JobHistory> jobHistoryGet(UserCredentials uc, Long jobHistoryId) {
        return this.jobHistoryCommand
            .jobHistoryGetCommand(uc.getLoginId(), jobHistoryId);
    }

    public Future<JobHistoryList> jobHistoryGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.jobHistoryCommand
            .jobHistoryGetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    

    //region JobTask

    public Future<Long> jobTaskAdd(UserCredentials uc, JobTask jobTask) {
        return this.jobTaskCommand
            .jobTaskAddCommand(uc.getLoginId(), jobTask);
    }
    
    public Future<Long> jobTaskUpdate(UserCredentials uc, JobTask jobTask) {
        return this.jobTaskCommand
            .jobTaskUpdateCommand(uc.getLoginId(), jobTask);
    }

    public Future<Long> jobTaskDelete(UserCredentials uc, Long jobTaskId) {
        return this.jobTaskCommand
            .jobTaskDeleteCommand(uc.getLoginId(), jobTaskId);
    }

    public Future<JobTask> jobTaskGet(UserCredentials uc, Long jobTaskId) {
        return this.jobTaskCommand
            .jobTaskGetCommand(uc.getLoginId(), jobTaskId);
    }

    public Future<JobTaskList> jobTaskGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.jobTaskCommand
            .jobTaskGetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    
}