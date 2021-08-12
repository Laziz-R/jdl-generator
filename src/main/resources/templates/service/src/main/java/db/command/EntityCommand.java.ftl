<#assign camelName = entity.name.camelCase/>
<#assign pascalName = entity.name.pascalCase/>
<#assign snakeName = entity.name.snakeCase/>
<#assign tableName = "${schema.snakeCase}.${snakeName}"/>
package ${package}.db.command;

import ${package}.model.${pascalName};
import ${package}.model.list.${pascalName}List;
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

public class ${pascalName}Command {
    private static Logger LOGGER = Logger.getLogger(${pascalName}Command.class);
    private PgPool client;

    public ${pascalName}Command(PgPool client) {
        this.client = client;
    }

    public Future<Long> ${camelName}AddCommand(Long loginId, ${pascalName} ${camelName}) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT ${tableName}_add(<#list 1..entity.fields?size+1 as i>$${i}<#sep>, </#list>) \"${snakeName}_id\";")
            .execute(
                Tuple.of(
                    loginId,
<#list entity.fields as field>
                    ${camelName}.get${field.name.pascalCase}()<#sep>,
</#list>

                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("${snakeName}_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> ${camelName}UpdateCommand(Long loginId, ${pascalName} ${camelName}) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT ${tableName}_update(<#list 1..entity.fields?size+2 as i>$${i}<#sep>, </#list>) \"${snakeName}_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    ${camelName}.get${pascalName}Id(),
<#list entity.fields as field>
                    ${camelName}.get${field.name.pascalCase}()<#sep>,
</#list>

                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("${snakeName}_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> ${camelName}DeleteCommand(Long loginId, Long ${camelName}Id) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT ${tableName}_delete($1, $2) \"${snakeName}_id\";")
            .execute(Tuple.of(loginId, ${camelName}Id))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("${snakeName}_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<${pascalName}> ${camelName}GetCommand(Long loginId, Long ${camelName}Id) {
        Promise<${pascalName}> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM ${tableName}_get($1, $2);")
            .execute(Tuple.of(loginId, ${camelName}Id))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(create${pascalName}(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<${pascalName}List> ${camelName}GetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<${pascalName}List> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM ${tableName}_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                ${pascalName}List ${camelName}List = new ${pascalName}List();
                res.forEach(row -> ${camelName}List.add(create${pascalName}(row)));
                promise.complete(${camelName}List);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private ${pascalName} create${pascalName}(Row row){
        return new ${pascalName}()
            .set${pascalName}Id(row.getLong("${snakeName}_id"))
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