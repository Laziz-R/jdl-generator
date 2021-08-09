package com.safenetpay.task.db.command;

import com.safenetpay.task.model.Region;
import com.safenetpay.task.model.list.RegionList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class RegionCommand {
    private static Logger LOGGER = Logger.getLogger(RegionCommand.class);
    private PgPool client;

    public RegionCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> regionAddCommand(Long loginId, Region region) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.region_add($1, $2) \"region_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    region.getRegionName()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("region_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> regionUpdateCommand(Long loginId, Region region) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.region_update($1, $2, $3) \"region_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    region.getRegionId(),
                    region.getRegionName()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("region_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> regionDeleteCommand(Long loginId, Long regionId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.region_delete($1, $2) \"region_id\";")
            .execute(Tuple.of(loginId, regionId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("region_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Region> regionGetCommand(Long loginId, Long regionId) {
        Promise<Region> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.region_get($1, $2);")
            .execute(Tuple.of(loginId, regionId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createRegion(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<RegionList> regionGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<RegionList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.region_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                RegionList regionList = new RegionList();
                res.forEach(row -> regionList.add(createRegion(row)));
                promise.complete(regionList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private Region createRegion(Row row){
        return new Region()
            .setRegionId(row.getLong("region_id"))
            .setRegionName(row.getString("region_name"))
;
    }
}