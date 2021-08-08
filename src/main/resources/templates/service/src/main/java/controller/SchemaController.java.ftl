<#assign serviceType = "${schema.pascalCase}Service"/>
<#assign serviceName = "${schema.camelCase}Service"/>
package ${package}.controller;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import ${package}.db.${serviceType};
<#list entities as entity>
import ${package}.model.${entity.name.pascalCase};
import ${package}.model.list.${entity.name.pascalCase}List;
</#list>
import ${package}.need.ApplicationRuntimeException;
import ${package}.need.Error;
import ${package}.need.UserCredentials;

import org.apache.log4j.Logger;


public class ${schema.pascalCase}Controller extends BaseController{
    private static Logger LOGGER = Logger.getLogger(${schema.pascalCase}Controller.class);

    private Vertx vertx;
    private ${serviceType} ${serviceName};

    public ${schema.pascalCase}Controller(Vertx vertx) {
        this.vertx = vertx;
        this.${serviceName} = new ${serviceType}(vertx);
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


    <#list entities as entity>
    <#assign tablePascal = entity.name.pascalCase/>
    <#assign tableCamel  = entity.name.camelCase/>
    <#assign tableSnake  = entity.name.snakeCase/>
    
    // region ${tablePascal} handler
    
    /**
    * ${tablePascal}Add handler.
    *
    * @param context - context
    */
    public void handle${tablePascal}Add(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("${tableSnake}") != null) {
                try{                    
                    ${tablePascal} ${tableCamel} = ${tablePascal}.fromJsonObject(params.getJsonObject("${tableSnake}").encodePrettily(), ${tablePascal}.class);
                    Future<Long> future${tablePascal} = ${serviceName}.${tableCamel}Add(credentials, ${tableCamel});
                    future${tablePascal}
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("${tableSnake}_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "${schema.pascalCase} ${tablePascal}Add failed.",
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
    * ${tablePascal}Delete handler.
    *
    * @param context - context
    */
    public void handle${tablePascal}Delete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long ${tableCamel}Id = params.getLong("${tableSnake}_id");
            Future<Long> future${tablePascal} = ${serviceName}.${tableCamel}Delete(credentials, ${tableCamel}Id);
            future${tablePascal}
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("${tableSnake}_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "${schema.pascalCase} ${tablePascal}Delete failed.",
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
    * ${tablePascal}Update handler.
    *
    * @param context - context
    */
    public void handle${tablePascal}Update(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("${tableSnake}") != null) {
                try{                    
                    ${tablePascal} ${tableCamel} = ${tablePascal}.fromJsonObject(params.getJsonObject("${tableSnake}").encodePrettily(), ${tablePascal}.class);
                    Future<Long> future${tablePascal} = ${serviceName}.${tableCamel}Update(credentials, ${tableCamel});
                    future${tablePascal}
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("${tableSnake}_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "${schema.pascalCase} ${tablePascal}Update failed.",
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
    * ${tablePascal}Get handler.
    *
    * @param context - routing context
    */
    public void handle${tablePascal}Get(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long ${tableCamel}Id = params.getLong("${tableSnake}_id");
            Future<${tablePascal}> future${tablePascal} = ${serviceName}.${tableCamel}Get(credentials, ${tableCamel}Id);
            future${tablePascal}
                .onSuccess(res -> {
                    ${tablePascal} ${tableCamel} = res;
                    if (${tableCamel} == null) {
                        throw new ApplicationRuntimeException(
                            "${schema.pascalCase} ${tablePascal}Get failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("${tableSnake}", ${tableCamel}.toJsonObject()), null);
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
    * ${tablePascal}GetList handler.
    *
    * @param context - routing context
    */
    public void handle${tablePascal}GetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<${tablePascal}List> future${tablePascal}List = ${serviceName}.${tableCamel}GetList(credentials, skip, pageSize);
            future${tablePascal}List
                .onSuccess(res -> {
                    ${tablePascal}List ${tableCamel}List = res;
                    if (${tableCamel}List == null) {
                        throw new ApplicationRuntimeException(
                            "${schema.pascalCase} ${tablePascal}GetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(${tableCamel}List.getLabel(), ${tableCamel}List.toJsonArray()), null);
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

    </#list>

}