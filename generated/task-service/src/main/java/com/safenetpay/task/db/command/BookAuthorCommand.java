package com.safenetpay.task.db.command;

import com.safenetpay.task.model.BookAuthor;
import com.safenetpay.task.model.list.BookAuthorList;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class BookAuthorCommand {
    private static Logger LOGGER = Logger.getLogger(BookAuthorCommand.class);
    private PgPool client;

    /**
    * Instantiates a new BookAuthorCommand.
    *
    * @param client the client
    */
    public BookAuthorCommand(PgPool client) {
        super();
        LOGGER.info("init: Creating BookAuthorCommand - start");
        this.client = client;
        LOGGER.info("init: Creating BookAuthorCommand - completed");
    }

    /**
    * Add bookAuthor command future.
    *
    * @param loginId the login id
    * @param bookAuthor the bookAuthor
    * @return the future
    */
    public Future<Long> bookAuthorAddCommand(Long loginId, BookAuthor bookAuthor) {
        LOGGER.info("info: bookAuthorAddCommand - start");
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.book_author_add($1, $2, $3) \"book_author_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    bookAuthor.getBookId(),
                    bookAuthor.getAuthorId()
                )
            )
            .onSuccess(res -> {
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query bookAuthorAddCommand result - ok");
                  promise.complete(row.getLong("book_author_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query bookAuthorAddCommand result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookAuthorAddCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * Update bookAuthor command future.
    *
    * @param loginId the login id
    * @param bookAuthor the bookAuthor
    * @return the future
    */
    public Future<Long> bookAuthorUpdateCommand(Long loginId, BookAuthor bookAuthor) {
        LOGGER.info("info: bookAuthorUpdateCommand - start");
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.book_author_update($1, $2, $3, $4) \"book_author_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    bookAuthor.getBookAuthorId(),
                    bookAuthor.getBookId(),
                    bookAuthor.getAuthorId()
                )
            )
            .onSuccess(res -> {
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query bookAuthorUpdateCommand result - ok");
                  promise.complete(row.getLong("book_author_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query bookAuthorUpdateCommand result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookAuthorUpdateCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * Delete bookAuthor command future.
    *
    * @param loginId the login id
    * @param bookAuthorId the bookAuthor id
    * @return the future
    */
    public Future<Long> bookAuthorDeleteCommand(Long loginId, Long bookAuthorId) {
        LOGGER.info("info: bookAuthorDeleteCommand - start");
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.book_author_delete($1, $2) \"book_author_id\";")
            .execute(Tuple.of(loginId, bookAuthorId))
            .onSuccess(res -> {
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query bookAuthorDeleteCommand result - ok");
                  promise.complete(row.getLong("book_author_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query bookAuthorDeleteCommand result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookAuthorDeleteCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * Get bookAuthor command future.
    *
    * @param loginId the login id
    * @param bookAuthorId the bookAuthor id
    * @return the bookAuthor command
    */
    public Future<BookAuthor> bookAuthorGetCommand(Long loginId, Long bookAuthorId) {
        LOGGER.info("info: bookAuthorGetCommand - start");
        Promise<BookAuthor> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_author_get($1, $2);")
            .execute(Tuple.of(loginId, bookAuthorId))
            .onSuccess(res -> {
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        BookAuthor bookAuthor = createBookAuthor(row);
                        LOGGER.info("info: handle query bookAuthorGetCommand result - ok");
                        promise.complete(bookAuthor);
                    }

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query bookAuthorGetCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query bookAuthorGetCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookAuthorGetCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * GetList bookAuthor command future.
    *
    * @param loginId the login id
    * @param skip the skip
    * @param pageSize the page size
    * @return the bookAuthor command
    */
    public Future<BookAuthorList> bookAuthorGetListCommand(Long loginId, Long skip, Long pageSize) {
        LOGGER.info("info: bookAuthorGetListCommand - start");
        Promise<BookAuthorList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_author_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                BookAuthorList bookAuthorList = new BookAuthorList();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        BookAuthor bookAuthor = createBookAuthor(row);
                        LOGGER.info("info: handle query bookAuthorGetListCommand result - ok");
                        bookAuthorList.add(bookAuthor);
                    }

                    promise.complete(bookAuthor);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query bookAuthorGetListCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query bookAuthorGetListCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookAuthorGetListCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * GetAll bookAuthor command future.
    *
    * @param loginId the login id
    * @return the bookAuthor command
    */
    public Future<BookAuthorList> bookAuthorGetAllCommand(Long loginId) {
        LOGGER.info("info: bookAuthorGetAllCommand - start");
        Promise<BookAuthorList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_author_get_all($1);")
            .execute(Tuple.of(loginId))
            .onSuccess(res -> {
                BookAuthorList bookAuthorList = new BookAuthorList();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        BookAuthor bookAuthor = createBookAuthor(row);
                        LOGGER.info("info: handle query bookAuthorGetAllCommand result - ok");
                        bookAuthorList.add(bookAuthor);
                    }

                    promise.complete(bookAuthor);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query bookAuthorGetAllCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query bookAuthorGetAllCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookAuthorGetListCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    /**
    * GetSummaryList bookAuthor command future.
    *
    * @param loginId the login id
    * @param sortExpression the sort expression
    * @param filterCondition the filter condition
    * @param skip the skip
    * @param pageSize the page size
    * @return the bookAuthor command
    */
    public Future<BookAuthorList> bookAuthorGetListCommand(Long loginId, String sortExpression, String filterCondition, Long skip, Long pageSize) {
        LOGGER.info("info: bookAuthorGetListCommand - start");
        Promise<BookAuthorList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_author_get_summary_list($1, $2, $3, $4, $5);")
            .execute(Tuple.of(loginId, sortExpression, filterCondition, skip, pageSize))
            .onSuccess(res -> {
                BookAuthorList bookAuthorList = new BookAuthorList();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        BookAuthor bookAuthor = createBookAuthor(row);
                        LOGGER.info("info: handle query bookAuthorGetListCommand result - ok");
                        bookAuthorList.add(bookAuthor);
                    }

                    promise.complete(bookAuthor);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query bookAuthorGetListCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query bookAuthorGetListCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookAuthorGetListCommand result - failed");
                promise.fail(ar.cause());
            });
        return promise.future();
    }

    private BookAuthor createBookAuthor(Row row){
        return new BookAuthor()
            .setBookAuthorId(row.getLong("book_author_id"))
            .setBookId(row.getLong("book_id"))
            .setAuthorId(row.getLong("author_id"));
    }
}