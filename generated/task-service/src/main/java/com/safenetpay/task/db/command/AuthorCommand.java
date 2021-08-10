package com.safenetpay.task.db.command;

import com.safenetpay.task.model.Author;
import com.safenetpay.task.model.list.AuthorList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class AuthorCommand {
    private static Logger LOGGER = Logger.getLogger(AuthorCommand.class);
    private PgPool client;

    public AuthorCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> authorAddCommand(Long loginId, Author author) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.author_add($1, $2, $3) \"author_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    author.getFullName(),
                    author.getBio()
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("author_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> authorUpdateCommand(Long loginId, Author author) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.author_update($1, $2, $3, $4) \"author_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    author.getAuthorId(),
                    author.getFullName(),
                    author.getBio()
                )
            )
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("author_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> authorDeleteCommand(Long loginId, Long authorId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.author_delete($1, $2) \"author_id\";")
            .execute(Tuple.of(loginId, authorId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("author_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Author> authorGetCommand(Long loginId, Long authorId) {
        Promise<Author> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.author_get($1, $2);")
            .execute(Tuple.of(loginId, authorId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createAuthor(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<AuthorList> authorGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<AuthorList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.author_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                AuthorList authorList = new AuthorList();
                res.forEach(row -> authorList.add(createAuthor(row)));
                promise.complete(authorList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private Author createAuthor(Row row){
        return new Author()
            .setAuthorId(row.getLong("author_id"))

            .setFullName(row.getString("full_name"))

            .setBio(row.getString("bio"));
    }
}