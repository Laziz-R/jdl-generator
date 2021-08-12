<#assign servicePascal = "${schema.pascalCase}Service"/>
package ${package}.db;

<#list entities as entity>
import ${package}.db.command.${entity.name.pascalCase}Command;
</#list>
<#list entities as entity>
import ${package}.model.${entity.name.pascalCase};
</#list>
<#list entities as entity>
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

public class ${servicePascal} {
    private static Logger LOGGER = Logger.getLogger(${servicePascal}.class);

<#list entities as entity>
    private ${entity.name.pascalCase}Command ${entity.name.camelCase}Command; 
</#list>
    private Vertx vertx;

    /**
    * Constructor.
    *
    * @param vertx - main vertex
    * @param config - json configuration
    */
    public ${servicePascal} (Vertx vertx){
        LOGGER.info("init: Creating ${servicePascal} - start");
        this.vertx = vertx;
        JsonObject dbConfig = vertx.getOrCreateContext().config().getJsonObject("db");
        PgConnectOptions connectOptions = new PgConnectOptions()
            .setPort(dbConfig.getInteger("port"))
            .setHost(dbConfig.getString("host"))
            .setDatabase(dbConfig.getString("db_name"))
            .setUser(dbConfig.getString("user"))
            .setPassword(dbConfig.getString("password"));
            
        PoolOptions poolOptions = new PoolOptions().setMaxSize(dbConfig.getInteger("max_pool_size"));
        PgPool client = PgPool.pool(vertx, connectOptions, poolOptions);
        <#list entities as entity>
        this.${entity.name.camelCase}Command = new ${entity.name.pascalCase}Command(client); 
        </#list>

        LOGGER.info("init: Creating ${servicePascal} - completed");
    }

<#list entities as entity>
<#assign tableCamel = entity.name.camelCase/>
<#assign tablePascal = entity.name.pascalCase/>

    //region ${tablePascal}

    public Future<Long> ${tableCamel}Add(UserCredentials uc, ${tablePascal} ${tableCamel}) {
        return this.${tableCamel}Command
            .${tableCamel}AddCommand(uc.getLoginId(), ${tableCamel});
    }
    
    public Future<Long> ${tableCamel}Update(UserCredentials uc, ${tablePascal} ${tableCamel}) {
        return this.${tableCamel}Command
            .${tableCamel}UpdateCommand(uc.getLoginId(), ${tableCamel});
    }

    public Future<Long> ${tableCamel}Delete(UserCredentials uc, Long ${tableCamel}Id) {
        return this.${tableCamel}Command
            .${tableCamel}DeleteCommand(uc.getLoginId(), ${tableCamel}Id);
    }

    public Future<${tablePascal}> ${tableCamel}Get(UserCredentials uc, Long ${tableCamel}Id) {
        return this.${tableCamel}Command
            .${tableCamel}GetCommand(uc.getLoginId(), ${tableCamel}Id);
    }

    public Future<${tablePascal}List> ${tableCamel}GetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.${tableCamel}Command
            .${tableCamel}GetListCommand(uc.getLoginId(), skip, pageSize);
    }
    
    //endregion
    
</#list>
}