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

    public BookAuthorCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> bookAuthorAddCommand(Long loginId, BookAuthor bookAuthor) {
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
                res.forEach(row -> promise.complete(row.getLong("book_author_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> bookAuthorUpdateCommand(Long loginId, BookAuthor bookAuthor) {
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
                res.forEach(row -> promise.complete(row.getLong("book_author_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> bookAuthorDeleteCommand(Long loginId, Long bookAuthorId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.book_author_delete($1, $2) \"book_author_id\";")
            .execute(Tuple.of(loginId, bookAuthorId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("book_author_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<BookAuthor> bookAuthorGetCommand(Long loginId, Long bookAuthorId) {
        Promise<BookAuthor> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_author_get($1, $2);")
            .execute(Tuple.of(loginId, bookAuthorId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createBookAuthor(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<BookAuthorList> bookAuthorGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<BookAuthorList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_author_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                BookAuthorList bookAuthorList = new BookAuthorList();
                res.forEach(row -> bookAuthorList.add(createBookAuthor(row)));
                promise.complete(bookAuthorList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private BookAuthor createBookAuthor(Row row){
        return new BookAuthor()
            .setBookAuthorId(row.getLong("book_author_id"))

            .setBookId(row.getLong("book_id"))

            .setAuthorId(row.getLong("author_id"));
    }
}