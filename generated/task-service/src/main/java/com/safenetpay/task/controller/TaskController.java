package com.safenetpay.task.controller;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import com.safenetpay.task.db.TaskService;
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
import com.safenetpay.task.need.ApplicationRuntimeException;
import com.safenetpay.task.need.Error;
import com.safenetpay.task.need.UserCredentials;

import org.apache.log4j.Logger;


public class TaskController extends BaseController{
    private static Logger LOGGER = Logger.getLogger(TaskController.class);

    private Vertx vertx;
    private TaskService taskService;

    public TaskController(Vertx vertx) {
        this.vertx = vertx;
        this.taskService = new TaskService(vertx);
    }


    //  #region  Common handlers

    /**
    * Default SomeSchemaServiceController fail handler.
    *
    * @param context - context
    */
    public void defaultFailureHandler(RoutingContext context) {
        try {
            if (context.failure() instanceof ApplicationRuntimeException) {
                ApplicationRuntimeException ex = (ApplicationRuntimeException) context.failure();
                LOGGER.error("info: SomeSchemaServiceController default failure handler");
                this.respondJsonResult(
                    context,
                    200,
                    this.getId(context),
                    null,
                    new JsonObject()
                        .put("code", ex.getError().getCode())
                        .put("message", ex.toString())
                );
            } else {
                LOGGER.error("info: SomeSchemaServiceController default failure handler");
                this.respondJsonResult(
                    context,
                    200,
                    this.getId(context),
                    null,
                    new JsonObject()
                        .put("code", -1)
                        .put("message", context.failure().getMessage())
                );
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            this.respondJsonResult(
                context,
                200,
                this.getId(context),
                null,
                new JsonObject()
                    .put("code", -1)
                    .put("message",
                        String.format("unknown error: Method %s for path %s with message %s",
                            context.request().method(),
                            context.request().absoluteURI(),
                            e.getMessage())
                    )
            );
        }
    }

    //  #endregion


    
    // region Region handler
    
    /**
    * RegionAdd handler.
    *
    * @param context - context
    */
    public void handleRegionAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("region") != null) {
                try{                    
                    Region region = Region.fromJsonObject(params.getJsonObject("region").encodePrettily(), Region.class);
                    Future<Long> futureRegion = taskService.regionAdd(credentials, region);
                    futureRegion
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("region_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task RegionAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * RegionDelete handler.
    *
    * @param context - context
    */
    public void handleRegionDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long regionId = params.getLong("region_id");
            Future<Long> futureRegion = taskService.regionDelete(credentials, regionId);
            futureRegion
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("region_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task RegionDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * RegionUpdate handler.
    *
    * @param context - context
    */
    public void handleRegionUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("region") != null) {
                try{                    
                    Region region = Region.fromJsonObject(params.getJsonObject("region").encodePrettily(), Region.class);
                    Future<Long> futureRegion = taskService.regionUpdate(credentials, region);
                    futureRegion
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("region_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task RegionUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * RegionGet handler.
    *
    * @param context - routing context
    */
    public void handleRegionGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long regionId = params.getLong("region_id");
            Future<Region> futureRegion = taskService.regionGet(credentials, regionId);
            futureRegion
                .onSuccess(res -> {
                    Region region = res;
                    if (region == null) {
                        throw new ApplicationRuntimeException(
                            "Task RegionGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("region", region.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * RegionGetList handler.
    *
    * @param context - routing context
    */
    public void handleRegionGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<RegionList> futureRegionList = taskService.regionGetList(credentials, skip, pageSize);
            futureRegionList
                .onSuccess(res -> {
                    RegionList regionList = res;
                    if (regionList == null) {
                        throw new ApplicationRuntimeException(
                            "Task RegionGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(regionList.getLabel(), regionList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion

    
    // region Country handler
    
    /**
    * CountryAdd handler.
    *
    * @param context - context
    */
    public void handleCountryAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("country") != null) {
                try{                    
                    Country country = Country.fromJsonObject(params.getJsonObject("country").encodePrettily(), Country.class);
                    Future<Long> futureCountry = taskService.countryAdd(credentials, country);
                    futureCountry
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("country_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task CountryAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * CountryDelete handler.
    *
    * @param context - context
    */
    public void handleCountryDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long countryId = params.getLong("country_id");
            Future<Long> futureCountry = taskService.countryDelete(credentials, countryId);
            futureCountry
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("country_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task CountryDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * CountryUpdate handler.
    *
    * @param context - context
    */
    public void handleCountryUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("country") != null) {
                try{                    
                    Country country = Country.fromJsonObject(params.getJsonObject("country").encodePrettily(), Country.class);
                    Future<Long> futureCountry = taskService.countryUpdate(credentials, country);
                    futureCountry
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("country_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task CountryUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * CountryGet handler.
    *
    * @param context - routing context
    */
    public void handleCountryGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long countryId = params.getLong("country_id");
            Future<Country> futureCountry = taskService.countryGet(credentials, countryId);
            futureCountry
                .onSuccess(res -> {
                    Country country = res;
                    if (country == null) {
                        throw new ApplicationRuntimeException(
                            "Task CountryGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("country", country.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * CountryGetList handler.
    *
    * @param context - routing context
    */
    public void handleCountryGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<CountryList> futureCountryList = taskService.countryGetList(credentials, skip, pageSize);
            futureCountryList
                .onSuccess(res -> {
                    CountryList countryList = res;
                    if (countryList == null) {
                        throw new ApplicationRuntimeException(
                            "Task CountryGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(countryList.getLabel(), countryList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion

    
    // region Location handler
    
    /**
    * LocationAdd handler.
    *
    * @param context - context
    */
    public void handleLocationAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("location") != null) {
                try{                    
                    Location location = Location.fromJsonObject(params.getJsonObject("location").encodePrettily(), Location.class);
                    Future<Long> futureLocation = taskService.locationAdd(credentials, location);
                    futureLocation
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("location_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task LocationAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * LocationDelete handler.
    *
    * @param context - context
    */
    public void handleLocationDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long locationId = params.getLong("location_id");
            Future<Long> futureLocation = taskService.locationDelete(credentials, locationId);
            futureLocation
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("location_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task LocationDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * LocationUpdate handler.
    *
    * @param context - context
    */
    public void handleLocationUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("location") != null) {
                try{                    
                    Location location = Location.fromJsonObject(params.getJsonObject("location").encodePrettily(), Location.class);
                    Future<Long> futureLocation = taskService.locationUpdate(credentials, location);
                    futureLocation
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("location_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task LocationUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * LocationGet handler.
    *
    * @param context - routing context
    */
    public void handleLocationGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long locationId = params.getLong("location_id");
            Future<Location> futureLocation = taskService.locationGet(credentials, locationId);
            futureLocation
                .onSuccess(res -> {
                    Location location = res;
                    if (location == null) {
                        throw new ApplicationRuntimeException(
                            "Task LocationGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("location", location.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * LocationGetList handler.
    *
    * @param context - routing context
    */
    public void handleLocationGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<LocationList> futureLocationList = taskService.locationGetList(credentials, skip, pageSize);
            futureLocationList
                .onSuccess(res -> {
                    LocationList locationList = res;
                    if (locationList == null) {
                        throw new ApplicationRuntimeException(
                            "Task LocationGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(locationList.getLabel(), locationList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion

    
    // region Department handler
    
    /**
    * DepartmentAdd handler.
    *
    * @param context - context
    */
    public void handleDepartmentAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("department") != null) {
                try{                    
                    Department department = Department.fromJsonObject(params.getJsonObject("department").encodePrettily(), Department.class);
                    Future<Long> futureDepartment = taskService.departmentAdd(credentials, department);
                    futureDepartment
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("department_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task DepartmentAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * DepartmentDelete handler.
    *
    * @param context - context
    */
    public void handleDepartmentDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long departmentId = params.getLong("department_id");
            Future<Long> futureDepartment = taskService.departmentDelete(credentials, departmentId);
            futureDepartment
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("department_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task DepartmentDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * DepartmentUpdate handler.
    *
    * @param context - context
    */
    public void handleDepartmentUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("department") != null) {
                try{                    
                    Department department = Department.fromJsonObject(params.getJsonObject("department").encodePrettily(), Department.class);
                    Future<Long> futureDepartment = taskService.departmentUpdate(credentials, department);
                    futureDepartment
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("department_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task DepartmentUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * DepartmentGet handler.
    *
    * @param context - routing context
    */
    public void handleDepartmentGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long departmentId = params.getLong("department_id");
            Future<Department> futureDepartment = taskService.departmentGet(credentials, departmentId);
            futureDepartment
                .onSuccess(res -> {
                    Department department = res;
                    if (department == null) {
                        throw new ApplicationRuntimeException(
                            "Task DepartmentGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("department", department.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * DepartmentGetList handler.
    *
    * @param context - routing context
    */
    public void handleDepartmentGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<DepartmentList> futureDepartmentList = taskService.departmentGetList(credentials, skip, pageSize);
            futureDepartmentList
                .onSuccess(res -> {
                    DepartmentList departmentList = res;
                    if (departmentList == null) {
                        throw new ApplicationRuntimeException(
                            "Task DepartmentGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(departmentList.getLabel(), departmentList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion

    
    // region Task handler
    
    /**
    * TaskAdd handler.
    *
    * @param context - context
    */
    public void handleTaskAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("task") != null) {
                try{                    
                    Task task = Task.fromJsonObject(params.getJsonObject("task").encodePrettily(), Task.class);
                    Future<Long> futureTask = taskService.taskAdd(credentials, task);
                    futureTask
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("task_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task TaskAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * TaskDelete handler.
    *
    * @param context - context
    */
    public void handleTaskDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long taskId = params.getLong("task_id");
            Future<Long> futureTask = taskService.taskDelete(credentials, taskId);
            futureTask
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("task_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task TaskDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * TaskUpdate handler.
    *
    * @param context - context
    */
    public void handleTaskUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("task") != null) {
                try{                    
                    Task task = Task.fromJsonObject(params.getJsonObject("task").encodePrettily(), Task.class);
                    Future<Long> futureTask = taskService.taskUpdate(credentials, task);
                    futureTask
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("task_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task TaskUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * TaskGet handler.
    *
    * @param context - routing context
    */
    public void handleTaskGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long taskId = params.getLong("task_id");
            Future<Task> futureTask = taskService.taskGet(credentials, taskId);
            futureTask
                .onSuccess(res -> {
                    Task task = res;
                    if (task == null) {
                        throw new ApplicationRuntimeException(
                            "Task TaskGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("task", task.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * TaskGetList handler.
    *
    * @param context - routing context
    */
    public void handleTaskGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<TaskList> futureTaskList = taskService.taskGetList(credentials, skip, pageSize);
            futureTaskList
                .onSuccess(res -> {
                    TaskList taskList = res;
                    if (taskList == null) {
                        throw new ApplicationRuntimeException(
                            "Task TaskGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(taskList.getLabel(), taskList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion

    
    // region Employee handler
    
    /**
    * EmployeeAdd handler.
    *
    * @param context - context
    */
    public void handleEmployeeAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("employee") != null) {
                try{                    
                    Employee employee = Employee.fromJsonObject(params.getJsonObject("employee").encodePrettily(), Employee.class);
                    Future<Long> futureEmployee = taskService.employeeAdd(credentials, employee);
                    futureEmployee
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("employee_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task EmployeeAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * EmployeeDelete handler.
    *
    * @param context - context
    */
    public void handleEmployeeDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long employeeId = params.getLong("employee_id");
            Future<Long> futureEmployee = taskService.employeeDelete(credentials, employeeId);
            futureEmployee
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("employee_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task EmployeeDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * EmployeeUpdate handler.
    *
    * @param context - context
    */
    public void handleEmployeeUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("employee") != null) {
                try{                    
                    Employee employee = Employee.fromJsonObject(params.getJsonObject("employee").encodePrettily(), Employee.class);
                    Future<Long> futureEmployee = taskService.employeeUpdate(credentials, employee);
                    futureEmployee
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("employee_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task EmployeeUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * EmployeeGet handler.
    *
    * @param context - routing context
    */
    public void handleEmployeeGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long employeeId = params.getLong("employee_id");
            Future<Employee> futureEmployee = taskService.employeeGet(credentials, employeeId);
            futureEmployee
                .onSuccess(res -> {
                    Employee employee = res;
                    if (employee == null) {
                        throw new ApplicationRuntimeException(
                            "Task EmployeeGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("employee", employee.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * EmployeeGetList handler.
    *
    * @param context - routing context
    */
    public void handleEmployeeGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<EmployeeList> futureEmployeeList = taskService.employeeGetList(credentials, skip, pageSize);
            futureEmployeeList
                .onSuccess(res -> {
                    EmployeeList employeeList = res;
                    if (employeeList == null) {
                        throw new ApplicationRuntimeException(
                            "Task EmployeeGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(employeeList.getLabel(), employeeList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion

    
    // region Job handler
    
    /**
    * JobAdd handler.
    *
    * @param context - context
    */
    public void handleJobAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("job") != null) {
                try{                    
                    Job job = Job.fromJsonObject(params.getJsonObject("job").encodePrettily(), Job.class);
                    Future<Long> futureJob = taskService.jobAdd(credentials, job);
                    futureJob
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task JobAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobDelete handler.
    *
    * @param context - context
    */
    public void handleJobDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long jobId = params.getLong("job_id");
            Future<Long> futureJob = taskService.jobDelete(credentials, jobId);
            futureJob
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task JobDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobUpdate handler.
    *
    * @param context - context
    */
    public void handleJobUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("job") != null) {
                try{                    
                    Job job = Job.fromJsonObject(params.getJsonObject("job").encodePrettily(), Job.class);
                    Future<Long> futureJob = taskService.jobUpdate(credentials, job);
                    futureJob
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task JobUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobGet handler.
    *
    * @param context - routing context
    */
    public void handleJobGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long jobId = params.getLong("job_id");
            Future<Job> futureJob = taskService.jobGet(credentials, jobId);
            futureJob
                .onSuccess(res -> {
                    Job job = res;
                    if (job == null) {
                        throw new ApplicationRuntimeException(
                            "Task JobGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("job", job.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobGetList handler.
    *
    * @param context - routing context
    */
    public void handleJobGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<JobList> futureJobList = taskService.jobGetList(credentials, skip, pageSize);
            futureJobList
                .onSuccess(res -> {
                    JobList jobList = res;
                    if (jobList == null) {
                        throw new ApplicationRuntimeException(
                            "Task JobGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(jobList.getLabel(), jobList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion

    
    // region JobHistory handler
    
    /**
    * JobHistoryAdd handler.
    *
    * @param context - context
    */
    public void handleJobHistoryAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("job_history") != null) {
                try{                    
                    JobHistory jobHistory = JobHistory.fromJsonObject(params.getJsonObject("job_history").encodePrettily(), JobHistory.class);
                    Future<Long> futureJobHistory = taskService.jobHistoryAdd(credentials, jobHistory);
                    futureJobHistory
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_history_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task JobHistoryAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobHistoryDelete handler.
    *
    * @param context - context
    */
    public void handleJobHistoryDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long jobHistoryId = params.getLong("job_history_id");
            Future<Long> futureJobHistory = taskService.jobHistoryDelete(credentials, jobHistoryId);
            futureJobHistory
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_history_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task JobHistoryDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobHistoryUpdate handler.
    *
    * @param context - context
    */
    public void handleJobHistoryUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("job_history") != null) {
                try{                    
                    JobHistory jobHistory = JobHistory.fromJsonObject(params.getJsonObject("job_history").encodePrettily(), JobHistory.class);
                    Future<Long> futureJobHistory = taskService.jobHistoryUpdate(credentials, jobHistory);
                    futureJobHistory
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_history_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task JobHistoryUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobHistoryGet handler.
    *
    * @param context - routing context
    */
    public void handleJobHistoryGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long jobHistoryId = params.getLong("job_history_id");
            Future<JobHistory> futureJobHistory = taskService.jobHistoryGet(credentials, jobHistoryId);
            futureJobHistory
                .onSuccess(res -> {
                    JobHistory jobHistory = res;
                    if (jobHistory == null) {
                        throw new ApplicationRuntimeException(
                            "Task JobHistoryGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_history", jobHistory.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobHistoryGetList handler.
    *
    * @param context - routing context
    */
    public void handleJobHistoryGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<JobHistoryList> futureJobHistoryList = taskService.jobHistoryGetList(credentials, skip, pageSize);
            futureJobHistoryList
                .onSuccess(res -> {
                    JobHistoryList jobHistoryList = res;
                    if (jobHistoryList == null) {
                        throw new ApplicationRuntimeException(
                            "Task JobHistoryGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(jobHistoryList.getLabel(), jobHistoryList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion

    
    // region JobTask handler
    
    /**
    * JobTaskAdd handler.
    *
    * @param context - context
    */
    public void handleJobTaskAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("job_task") != null) {
                try{                    
                    JobTask jobTask = JobTask.fromJsonObject(params.getJsonObject("job_task").encodePrettily(), JobTask.class);
                    Future<Long> futureJobTask = taskService.jobTaskAdd(credentials, jobTask);
                    futureJobTask
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_task_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task JobTaskAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobTaskDelete handler.
    *
    * @param context - context
    */
    public void handleJobTaskDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long jobTaskId = params.getLong("job_task_id");
            Future<Long> futureJobTask = taskService.jobTaskDelete(credentials, jobTaskId);
            futureJobTask
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_task_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task JobTaskDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobTaskUpdate handler.
    *
    * @param context - context
    */
    public void handleJobTaskUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("job_task") != null) {
                try{                    
                    JobTask jobTask = JobTask.fromJsonObject(params.getJsonObject("job_task").encodePrettily(), JobTask.class);
                    Future<Long> futureJobTask = taskService.jobTaskUpdate(credentials, jobTask);
                    futureJobTask
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_task_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task JobTaskUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobTaskGet handler.
    *
    * @param context - routing context
    */
    public void handleJobTaskGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long jobTaskId = params.getLong("job_task_id");
            Future<JobTask> futureJobTask = taskService.jobTaskGet(credentials, jobTaskId);
            futureJobTask
                .onSuccess(res -> {
                    JobTask jobTask = res;
                    if (jobTask == null) {
                        throw new ApplicationRuntimeException(
                            "Task JobTaskGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("job_task", jobTask.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * JobTaskGetList handler.
    *
    * @param context - routing context
    */
    public void handleJobTaskGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<JobTaskList> futureJobTaskList = taskService.jobTaskGetList(credentials, skip, pageSize);
            futureJobTaskList
                .onSuccess(res -> {
                    JobTaskList jobTaskList = res;
                    if (jobTaskList == null) {
                        throw new ApplicationRuntimeException(
                            "Task JobTaskGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(jobTaskList.getLabel(), jobTaskList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion


}