package com.safenetpay.task.db.command;

import com.safenetpay.task.model.Location;
import com.safenetpay.task.model.list.LocationList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class LocationCommand {
    private static Logger LOGGER = Logger.getLogger(LocationCommand.class);
    private PgPool client;

    public LocationCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> locationAddCommand(Long loginId, Location location) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.location_add($1, $2, $3, $4, $5, $6) \"location_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    location.getStreetAddress(),
                    location.getPostalCode(),
                    location.getCity(),
                    location.getStateProvince(),
                    location.getCountryId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("location_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> locationUpdateCommand(Long loginId, Location location) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.location_update($1, $2, $3, $4, $5, $6, $7) \"location_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    location.getLocationId(),
                    location.getStreetAddress(),
                    location.getPostalCode(),
                    location.getCity(),
                    location.getStateProvince(),
                    location.getCountryId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("location_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> locationDeleteCommand(Long loginId, Long locationId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.location_delete($1, $2) \"location_id\";")
            .execute(Tuple.of(loginId, locationId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("location_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Location> locationGetCommand(Long loginId, Long locationId) {
        Promise<Location> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.location_get($1, $2);")
            .execute(Tuple.of(loginId, locationId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createLocation(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<LocationList> locationGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<LocationList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.location_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                LocationList locationList = new LocationList();
                res.forEach(row -> locationList.add(createLocation(row)));
                promise.complete(locationList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private Location createLocation(Row row){
        return new Location()
            .setLocationId(row.getLong("location_id"))
            .setStreetAddress(row.getString("street_address"))
            .setPostalCode(row.getString("postal_code"))
            .setCity(row.getString("city"))
            .setStateProvince(row.getString("state_province"))
            .setCountryId(row.getLong("country_id"))
;
    }
}