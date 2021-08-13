<#assign schemaCamel = schema.camelCase/>
<#assign schemaPascal = schema.pascalCase/>
<#assign schemaKebab = schema.kebabCase/>
<#assign servicePascal = "${schemaPascal}Service"/>
<#assign serviceCamel = "${schemaCamel}Service"/>
<#assign controllerPascal = "${servicePascal}Controller"/>
<#assign controllerCamel = "${serviceCamel}Controller"/>
package ${package}.controller;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

import ${package}.db.${servicePascal};
<#list entities as entity>
import ${package}.model.${entity.name.pascalCase};
import ${package}.model.list.${entity.name.pascalCase}List;
</#list>
import ${package}.need.ApplicationRuntimeException;
import ${package}.need.Error;
import ${package}.need.UserCredentials;

import org.apache.log4j.Logger;

public class ${controllerPascal} extends BaseController{
    private static Logger LOGGER = Logger.getLogger(${controllerPascal}.class);

    private Vertx vertx;
    private ${servicePascal} ${serviceCamel};

    static final String HASH_SAULT = "put here any string you need";
    private WebClient authClient;
    private String serviceToken;

    /**
    * Constructor of ${controllerCamel}.
    *
    * @param vertx - default mounting vertex
    */
    public ${controllerPascal}(Vertx vertx) {
        super();
        LOGGER.info("init: ${controllerPascal} - start");
        this.vertx = vertx;

        JsonObject config = Vertx.currentContext().config();

        this.${serviceCamel} = new ${servicePascal}(vertx);
        this.serviceToken = config.getJsonObject("${servicePascal}").getString("token");

        WebClientOptions webClientOptions = new WebClientOptions()
            .setDefaultHost(config.getJsonObject("AuthService").getJsonObject("http").getString("host"))
            .setDefaultPort(config.getJsonObject("AuthService").getJsonObject("http").getInteger("port"));
        this.authClient = WebClient.create(vertx, webClientOptions);

        LOGGER.info("init: ${controllerPascal} - completed");
    }


    //  #region  Common handlers

    /**
    * Default ${controllerPascal} fail handler.
    *
    * @param context - context
    */
    public void defaultFailureHandler(RoutingContext context) {
        try {
            if (context.failure() instanceof ApplicationRuntimeException) {
                ApplicationRuntimeException ex = (ApplicationRuntimeException) context.failure();
                LOGGER.error("info: ${controllerPascal} default failure handler");
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
                LOGGER.error("info: ${controllerPascal} default failure handler");
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

    /**
    * Default ${controllerPascal} fail handler.
    *
    * @param context - context
    */
    public void handlerNotFound(RoutingContext context) {
        LOGGER.info("info: ${controllerPascal} default handler Not Found");
        HttpMethod method = context.request().method();
        this.respondJsonResult(
            context,
            404,
            10L,
            null,
            new JsonObject()
                .put("code", -1)
                .put("message",
                    String.format("unknown error: Method %s for path %s",
                    method, context.request().absoluteURI()))
        );
    }

    /**
    * Security handler.
    *
    * @param context - context
    */
    public void handleApiKeyAuth(RoutingContext context) {
        LOGGER.info("call: ${controllerPascal} ApiKeyAuth Handler");
        String tokenParam = context.request().headers().get("token");

        JsonObject jsonBody = new JsonObject()
            .put("jsonrpc", "2.0")
            .put("id", 1)
            .put("params", new JsonObject().put("user_id", 2).put("token", this.serviceToken));

        this.authClient
            .post("/auth/authenticate")
            .putHeader("token", tokenParam)
            .sendJsonObject(jsonBody)
            .onSuccess(response -> {
                JsonObject authResult = response.bodyAsJsonObject();
                if (authResult.getJsonObject("result") != null) {
                    context.put("auth", authResult.getJsonObject("result"));
                    context.next();
                } else {
                    throw new ApplicationRuntimeException(
                    authResult.getJsonObject("error").getString("message"), Error.AUTH);
                }
            })
            .onFailure(context::fail);
    }

    //  #endregion


    <#list entities as entity>
    <#assign tablePascal = entity.name.pascalCase/>
    <#assign tableCamel = entity.name.camelCase/>
    <#assign tableSnake = entity.name.snakeCase/>
    <#assign tableKebab = entity.name.kebabCase/>
    
    // region ${tablePascal} handler
    
    /**
    * ${tablePascal}Add handler.
    *
    * @param context - context
    */
    public void handle${tablePascal}Add(RoutingContext context) {
        try {
            LOGGER.info("call: ${controllerPascal} /${schemaKebab}/${tableKebab}/add Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            if (params.getJsonObject("${tableSnake}") != null) {
                try{
                    ${tablePascal} ${tableCamel} = ${tablePascal}.fromJsonObject(params.getJsonObject("${tableSnake}").encodePrettily(), ${tablePascal}.class);
                    Future<Long> future${tablePascal} = ${serviceCamel}.${tableCamel}Add(credentials, ${tableCamel});
                    future${tablePascal}
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("${tableSnake}_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "${schemaPascal} ${tablePascal}Add failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: ${controllerPascal} /${schemaKebab}/${tableKebab}/delete Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long ${tableCamel}Id = params.getLong("${tableSnake}_id");
            Future<Long> future${tablePascal} = ${serviceCamel}.${tableCamel}Delete(credentials, ${tableCamel}Id);
            future${tablePascal}
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("${tableSnake}_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "${schemaPascal} ${tablePascal}Delete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: ${controllerPascal} /${schemaKebab}/${tableKebab}/update Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));
            
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            if (params.getJsonObject("${tableSnake}") != null) {
                try{                    
                    ${tablePascal} ${tableCamel} = ${tablePascal}.fromJsonObject(params.getJsonObject("${tableSnake}").encodePrettily(), ${tablePascal}.class);
                    Future<Long> future${tablePascal} = ${serviceCamel}.${tableCamel}Update(credentials, ${tableCamel});
                    future${tablePascal}
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("${tableSnake}_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "${schemaPascal} ${tablePascal}Update failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: ${controllerPascal} /${schemaKebab}/${tableKebab}/get Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long ${tableCamel}Id = params.getLong("${tableSnake}_id");
            Future<${tablePascal}> future${tablePascal} = ${serviceCamel}.${tableCamel}Get(credentials, ${tableCamel}Id);
            future${tablePascal}
                .onSuccess(res -> {
                    ${tablePascal} ${tableCamel} = res;
                    if (${tableCamel} == null) {
                        throw new ApplicationRuntimeException(
                            "${schemaPascal} ${tablePascal}Get failed.",
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
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: ${controllerPascal} /${schemaKebab}/${tableKebab}/get-list Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<${tablePascal}List> future${tablePascal}List = ${serviceCamel}.${tableCamel}GetList(credentials, skip, pageSize);
            future${tablePascal}List
                .onSuccess(res -> {
                    ${tablePascal}List ${tableCamel}List = res;
                    if (${tableCamel}List == null) {
                        throw new ApplicationRuntimeException(
                            "${schemaPascal} ${tablePascal}GetList failed.",
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
            LOGGER.error(e.getMessage());
            context.fail(e);
        }
    }

    /**
    * ${tablePascal}GetAll handler.
    *
    * @param context - routing context
    */
    public void handle${tablePascal}GetAll(RoutingContext context) {
        try {
            LOGGER.info("call: ${controllerPascal} /${schemaKebab}/${tableKebab}/get-all Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            Future<${tablePascal}List> future${tablePascal}List = ${serviceCamel}.${tableCamel}GetAll(credentials);
            future${tablePascal}List
                .onSuccess(res -> {
                    ${tablePascal}List ${tableCamel}List = res;
                    if (${tableCamel}List == null) {
                        throw new ApplicationRuntimeException(
                            "${schemaPascal} ${tablePascal}GetAll failed.",
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
            LOGGER.error(e.getMessage());
            context.fail(e);
        }
    }

    /**
    * ${tablePascal}GetSummaryList handler.
    *
    * @param context - routing context
    */
    public void handle${tablePascal}GetSummaryList(RoutingContext context) {
        try {
            LOGGER.info("call: ${controllerPascal} /${schemaKebab}/${tableKebab}/get-summary-list Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            String sortExpression = params.getString("sort_expression");
            String filterCondition = params.getString("filter_condition");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<${tablePascal}List> future${tablePascal}List = ${serviceCamel}.${tableCamel}GetSummaryList(credentials, sortExpression, filterCondition, skip, pageSize);
            future${tablePascal}List
                .onSuccess(res -> {
                    ${tablePascal}List ${tableCamel}List = res;
                    if (${tableCamel}List == null) {
                        throw new ApplicationRuntimeException(
                            "${schemaPascal} ${tablePascal}GetSummaryList failed.",
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
            LOGGER.error(e.getMessage());
            context.fail(e);
        }
    }
    // endregion

    </#list>

}