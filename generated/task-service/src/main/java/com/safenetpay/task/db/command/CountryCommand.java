package com.safenetpay.task.db.command;

import com.safenetpay.task.model.Country;
import com.safenetpay.task.model.list.CountryList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class CountryCommand {
    private static Logger LOGGER = Logger.getLogger(CountryCommand.class);
    private PgPool client;

    public CountryCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> countryAddCommand(Long loginId, Country country) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.country_add($1, $2, $3) \"country_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    country.getCountryName(),
                    country.getRegionId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("country_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> countryUpdateCommand(Long loginId, Country country) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.country_update($1, $2, $3, $4) \"country_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    country.getCountryId(),
                    country.getCountryName(),
                    country.getRegionId()                
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("country_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> countryDeleteCommand(Long loginId, Long countryId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.country_delete($1, $2) \"country_id\";")
            .execute(Tuple.of(loginId, countryId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("country_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Country> countryGetCommand(Long loginId, Long countryId) {
        Promise<Country> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.country_get($1, $2);")
            .execute(Tuple.of(loginId, countryId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createCountry(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<CountryList> countryGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<CountryList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.country_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                CountryList countryList = new CountryList();
                res.forEach(row -> countryList.add(createCountry(row)));
                promise.complete(countryList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private Country createCountry(Row row){
        return new Country()
            .setCountryId(row.getLong("country_id"))
            .setCountryName(row.getString("country_name"))
            .setRegionId(row.getLong("region_id"))
;
    }
}