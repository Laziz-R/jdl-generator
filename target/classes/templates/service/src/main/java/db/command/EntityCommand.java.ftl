<#assign tableCamel = entity.name.camelCase/>
<#assign tablePascal = entity.name.pascalCase/>
<#assign tableSnake = entity.name.snakeCase/>
<#assign tableUri = "${schema.snakeCase}.${tableSnake}"/>
<#assign addFunction     = "${tableCamel}AddCommand"/>
<#assign deleteFunction  = "${tableCamel}DeleteCommand"/>
<#assign updateFunction  = "${tableCamel}UpdateCommand"/>
<#assign getFunction     = "${tableCamel}GetCommand"/>
<#assign getListFunction = "${tableCamel}GetListCommand"/>
<#assign getAllFunction  = "${tableCamel}GetAllCommand"/>
<#assign getSummaryListFunction = "${tableCamel}GetSummaryListCommand"/>
package ${package}.db.command;

import ${package}.model.${tablePascal};
import ${package}.model.list.${tablePascal}List;
<#list entity.fields as field>
<#if field.type.unknown>
import ${package}.model.${field.name.pascalCase};
</#if>
</#list>

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class ${tablePascal}Command {
    private static Logger LOGGER = Logger.getLogger(${tablePascal}Command.class);
    private PgPool client;

    /**
    * Instantiates a new ${tablePascal}Command.
    *
    * @param client the client
    */
    public ${tablePascal}Command(PgPool client) {
        super();
        LOGGER.info("init: Creating ${tablePascal}Command - start");
        this.client = client;
        LOGGER.info("init: Creating ${tablePascal}Command - completed");
    }

    /**
    * Add ${tableCamel} command future.
    *
    * @param loginId the login id
    * @param ${tableCamel} the ${tableCamel}
    * @return the future
    */
    public Future<Long> ${addFunction}(Long loginId, ${tablePascal} ${tableCamel}) {
        LOGGER.info("info: ${addFunction} - start");
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT ${tableUri}_add(<#list 1..entity.fields?size+1 as i>$${i}<#sep>, </#list>) \"${tableSnake}_id\";")
            .execute(
                Tuple.of(
                    loginId,
<#list entity.fields as field>
                    ${tableCamel}.get${field.name.pascalCase}()<#sep>,
</#list>

                )
            )
            .onSuccess(res -> {
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query ${addFunction} result - ok");
                  promise.complete(row.getLong("${tableSnake}_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query ${addFunction} result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query ${addFunction} result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * Update ${tableCamel} command future.
    *
    * @param loginId the login id
    * @param ${tableCamel} the ${tableCamel}
    * @return the future
    */
    public Future<Long> ${updateFunction}(Long loginId, ${tablePascal} ${tableCamel}) {
        LOGGER.info("info: ${updateFunction} - start");
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT ${tableUri}_update(<#list 1..entity.fields?size+2 as i>$${i}<#sep>, </#list>) \"${tableSnake}_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    ${tableCamel}.get${tablePascal}Id(),
<#list entity.fields as field>
                    ${tableCamel}.get${field.name.pascalCase}()<#sep>,
</#list>

                )
            )
            .onSuccess(res -> {
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query ${updateFunction} result - ok");
                  promise.complete(row.getLong("${tableSnake}_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query ${updateFunction} result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query ${updateFunction} result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * Delete ${tableCamel} command future.
    *
    * @param loginId the login id
    * @param ${tableCamel}Id the ${tableCamel} id
    * @return the future
    */
    public Future<Long> ${deleteFunction}(Long loginId, Long ${tableCamel}Id) {
        LOGGER.info("info: ${deleteFunction} - start");
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT ${tableUri}_delete($1, $2) \"${tableSnake}_id\";")
            .execute(Tuple.of(loginId, ${tableCamel}Id))
            .onSuccess(res -> {
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query ${deleteFunction} result - ok");
                  promise.complete(row.getLong("${tableSnake}_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query ${deleteFunction} result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query ${deleteFunction} result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * Get ${tableCamel} command future.
    *
    * @param loginId the login id
    * @param ${tableCamel}Id the ${tableCamel} id
    * @return the ${tableCamel} command
    */
    public Future<${tablePascal}> ${getFunction}(Long loginId, Long ${tableCamel}Id) {
        LOGGER.info("info: ${getFunction} - start");
        Promise<${tablePascal}> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM ${tableUri}_get($1, $2);")
            .execute(Tuple.of(loginId, ${tableCamel}Id))
            .onSuccess(res -> {
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        ${tablePascal} ${tableCamel} = create${tablePascal}(row);
                        LOGGER.info("info: handle query ${getFunction} result - ok");
                        promise.complete(${tableCamel});
                    }

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query ${getFunction} result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query ${getFunction} result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query ${getFunction} result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * GetList ${tableCamel} command future.
    *
    * @param loginId the login id
    * @param skip the skip
    * @param pageSize the page size
    * @return the ${tableCamel} command
    */
    public Future<${tablePascal}List> ${getListFunction}(Long loginId, Long skip, Long pageSize) {
        LOGGER.info("info: ${getListFunction} - start");
        Promise<${tablePascal}List> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM ${tableUri}_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                ${tablePascal}List ${tableCamel}List = new ${tablePascal}List();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        ${tablePascal} ${tableCamel} = create${tablePascal}(row);
                        LOGGER.info("info: handle query ${getListFunction} result - ok");
                        ${tableCamel}List.add(${tableCamel});
                    }

                    promise.complete(${tableCamel}List);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query ${getListFunction} result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query ${getListFunction} result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query ${getListFunction} result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * GetAll ${tableCamel} command future.
    *
    * @param loginId the login id
    * @return the ${tableCamel} command
    */
    public Future<${tablePascal}List> ${getAllFunction}(Long loginId) {
        LOGGER.info("info: ${getAllFunction} - start");
        Promise<${tablePascal}List> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM ${tableUri}_get_all($1);")
            .execute(Tuple.of(loginId))
            .onSuccess(res -> {
                ${tablePascal}List ${tableCamel}List = new ${tablePascal}List();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        ${tablePascal} ${tableCamel} = create${tablePascal}(row);
                        LOGGER.info("info: handle query ${getAllFunction} result - ok");
                        ${tableCamel}List.add(${tableCamel});
                    }

                    promise.complete(${tableCamel}List);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query ${getAllFunction} result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query ${getAllFunction} result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query ${getListFunction} result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * GetSummaryList ${tableCamel} command future.
    *
    * @param loginId the login id
    * @param sortExpression the sort expression
    * @param filterCondition the filter condition
    * @param skip the skip
    * @param pageSize the page size
    * @return the ${tableCamel} command
    */
    public Future<${tablePascal}List> ${getSummaryListFunction}(Long loginId, String sortExpression, String filterCondition, Long skip, Long pageSize) {
        LOGGER.info("info: ${getSummaryListFunction} - start");
        Promise<${tablePascal}List> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM ${tableUri}_get_summary_list($1, $2, $3, $4, $5);")
            .execute(Tuple.of(loginId, sortExpression, filterCondition, skip, pageSize))
            .onSuccess(res -> {
                ${tablePascal}List ${tableCamel}List = new ${tablePascal}List();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        ${tablePascal} ${tableCamel} = create${tablePascal}(row);
                        LOGGER.info("info: handle query ${getSummaryListFunction} result - ok");
                        ${tableCamel}List.add(${tableCamel});
                    }

                    promise.complete(${tableCamel}List);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query ${getSummaryListFunction} result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query ${getSummaryListFunction} result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query ${getSummaryListFunction} result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    private ${tablePascal} create${tablePascal}(Row row){
        return new ${tablePascal}()
            .set${tablePascal}Id(row.getLong("${tableSnake}_id"))
<#list entity.fields as field>
<#assign fieldPascal = field.name.pascalCase/>
<#assign fieldSnake = field.name.snakeCase/>
<#assign javaType = field.type.jvName/>
<#if !field?has_next><#assign post=';'/></#if>
<#if field.type.unknown>
            .set${fieldPascal}(row.getString("${fieldSnake}") == null
                ? null
                : ${fieldPascal}.valueOf(row.getString("${fieldSnake}")))${post!''}
<#else>
            .set${fieldPascal}(row.get${javaType}("${fieldSnake}"))${post!''}
</#if>
</#list>
    }
}