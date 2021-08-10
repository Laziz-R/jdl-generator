package ${package}.db;

<#list entities as entity>
import ${package}.db.command.${entity.name.pascalCase}Command;
</#list>
<#list entities as entity>
import ${package}.model.${entity.name.pascalCase};
import ${package}.model.list.${entity.name.pascalCase}List;
</#list>
import ${package}.need.UserCredentials;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

import org.apache.log4j.Logger;

public class ${schema.pascalCase}Service {
    private static Logger LOGGER = Logger.getLogger(${schema.pascalCase}Service.class);

<#list entities as entity>
    private ${entity.name.pascalCase}Command ${entity.name.camelCase}Command; 
</#list>
    private Vertx vertx;

    public ${schema.pascalCase}Service (Vertx vertx){
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
        <#list entities as entity>
        this.${entity.name.camelCase}Command = new ${entity.name.pascalCase}Command(client); 
        </#list>
    }
<#list entities as entity>
<#assign camelName = entity.name.camelCase/>
<#assign pascalName = entity.name.pascalCase/>

    //region ${pascalName}

    public Future<Long> ${camelName}Add(UserCredentials uc, ${pascalName} ${camelName}) {
        return this.${camelName}Command
            .${camelName}AddCommand(uc.getLoginId(), ${camelName});
    }
    
    public Future<Long> ${camelName}Update(UserCredentials uc, ${pascalName} ${camelName}) {
        return this.${camelName}Command
            .${camelName}UpdateCommand(uc.getLoginId(), ${camelName});
    }

    public Future<Long> ${camelName}Delete(UserCredentials uc, Long ${camelName}Id) {
        return this.${camelName}Command
            .${camelName}DeleteCommand(uc.getLoginId(), ${camelName}Id);
    }

    public Future<${pascalName}> ${camelName}Get(UserCredentials uc, Long ${camelName}Id) {
        return this.${camelName}Command
            .${camelName}GetCommand(uc.getLoginId(), ${camelName}Id);
    }

    public Future<${pascalName}List> ${camelName}GetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.${camelName}Command
            .${camelName}GetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    
</#list>
}