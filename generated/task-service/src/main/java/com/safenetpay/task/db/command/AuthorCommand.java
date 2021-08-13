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

    /**
    * Instantiates a new AuthorCommand.
    *
    * @param client the client
    */
    public AuthorCommand(PgPool client) {
        super();
        LOGGER.info("init: Creating AuthorCommand - start");
        this.client = client;
        LOGGER.info("init: Creating AuthorCommand - completed");
    }

    /**
    * Add author command future.
    *
    * @param loginId the login id
    * @param author the author
    * @return the future
    */
    public Future<Long> authorAddCommand(Long loginId, Author author) {
        LOGGER.info("info: authorAddCommand - start");
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
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query authorAddCommand result - ok");
                  promise.complete(row.getLong("author_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query authorAddCommand result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query authorAddCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * Update author command future.
    *
    * @param loginId the login id
    * @param author the author
    * @return the future
    */
    public Future<Long> authorUpdateCommand(Long loginId, Author author) {
        LOGGER.info("info: authorUpdateCommand - start");
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
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query authorUpdateCommand result - ok");
                  promise.complete(row.getLong("author_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query authorUpdateCommand result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query authorUpdateCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * Delete author command future.
    *
    * @param loginId the login id
    * @param authorId the author id
    * @return the future
    */
    public Future<Long> authorDeleteCommand(Long loginId, Long authorId) {
        LOGGER.info("info: authorDeleteCommand - start");
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.author_delete($1, $2) \"author_id\";")
            .execute(Tuple.of(loginId, authorId))
            .onSuccess(res -> {
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query authorDeleteCommand result - ok");
                  promise.complete(row.getLong("author_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query authorDeleteCommand result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query authorDeleteCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * Get author command future.
    *
    * @param loginId the login id
    * @param authorId the author id
    * @return the author command
    */
    public Future<Author> authorGetCommand(Long loginId, Long authorId) {
        LOGGER.info("info: authorGetCommand - start");
        Promise<Author> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.author_get($1, $2);")
            .execute(Tuple.of(loginId, authorId))
            .onSuccess(res -> {
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        Author author = createAuthor(row);
                        LOGGER.info("info: handle query authorGetCommand result - ok");
                        promise.complete(author);
                    }

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query authorGetCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query authorGetCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query authorGetCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * GetList author command future.
    *
    * @param loginId the login id
    * @param skip the skip
    * @param pageSize the page size
    * @return the author command
    */
    public Future<AuthorList> authorGetListCommand(Long loginId, Long skip, Long pageSize) {
        LOGGER.info("info: authorGetListCommand - start");
        Promise<AuthorList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.author_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                AuthorList authorList = new AuthorList();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        Author author = createAuthor(row);
                        LOGGER.info("info: handle query authorGetListCommand result - ok");
                        authorList.add(author);
                    }

                    promise.complete(author);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query authorGetListCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query authorGetListCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query authorGetListCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * GetAll author command future.
    *
    * @param loginId the login id
    * @return the author command
    */
    public Future<AuthorList> authorGetAllCommand(Long loginId) {
        LOGGER.info("info: authorGetAllCommand - start");
        Promise<AuthorList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.author_get_all($1);")
            .execute(Tuple.of(loginId))
            .onSuccess(res -> {
                AuthorList authorList = new AuthorList();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        Author author = createAuthor(row);
                        LOGGER.info("info: handle query authorGetAllCommand result - ok");
                        authorList.add(author);
                    }

                    promise.complete(author);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query authorGetAllCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query authorGetAllCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query authorGetListCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * GetSummaryList author command future.
    *
    * @param loginId the login id
    * @param sortExpression the sort expression
    * @param filterCondition the filter condition
    * @param skip the skip
    * @param pageSize the page size
    * @return the author command
    */
    public Future<AuthorList> authorGetListCommand(Long loginId, String sortExpression, String filterCondition, Long skip, Long pageSize) {
        LOGGER.info("info: authorGetListCommand - start");
        Promise<AuthorList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.author_get_summary_list($1, $2, $3, $4, $5);")
            .execute(Tuple.of(loginId, sortExpression, filterCondition, skip, pageSize))
            .onSuccess(res -> {
                AuthorList authorList = new AuthorList();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        Author author = createAuthor(row);
                        LOGGER.info("info: handle query authorGetListCommand result - ok");
                        authorList.add(author);
                    }

                    promise.complete(author);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query authorGetListCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query authorGetListCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query authorGetListCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    private Author createAuthor(Row row){
        return new Author()
            .setAuthorId(row.getLong("author_id"))
            .setFullName(row.getString("full_name"))
            .setBio(row.getString("bio"));
    }
}